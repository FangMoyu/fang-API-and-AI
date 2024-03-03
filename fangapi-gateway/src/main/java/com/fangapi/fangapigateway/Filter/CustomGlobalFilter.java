package com.fangapi.fangapigateway.Filter;

import cn.hutool.crypto.SignUtil;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.service.InnerInterfaceInfoService;
import com.fang.fangapicommon.service.InnerUserInterfaceInfoService;
import com.fang.fangapicommon.service.InnerUserService;
import com.fangapi.fangapiclientsdk.Utils.signUtils;
import com.fangapi.fangapigateway.Manager.RedissonRateLimitManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    //白名单，允许访问的 IP 地址
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    // 通用接口前缀
    private static final String BASE_URL = "http://localhost:8123";
    // AI 接口前缀
    private static final String AI_BASE_URL = "http://localhost:8101";
    private static final String LIMIT_KEY = "limit_key";

    @Resource
    private RedissonRateLimitManager redissonRateLimitManager;
    private int count = 0;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 统一限流
        boolean b = redissonRateLimitManager.doRateLimit(LIMIT_KEY);
        if(!b) {
            count++;
            System.out.println("限流了" + count + "次");
            handleInvokeError(exchange.getResponse());
        }
//        1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path;
        if(request.getPath().value().contains("ai")) {
            path = AI_BASE_URL + request.getPath().value();
        }else {
            path =BASE_URL + request.getPath().value();
        }
        // 日志记录
        String method = request.getMethod().toString();
        log.info("请求唯一标识" + request.getId());
        log.info("请求路径" + path);
        log.info("请求方法" + method);
        log.info("请求参数" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址:" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());

        // 流量染色
        // 添加一个自定义请求头，然后将经过 gateway 处理后的请求才能被接口接收
        request.mutate().headers(httpHeaders ->{
            httpHeaders.add("source","byGateway");
        } ).build();
//        2. （黑白名单）
        //拿到响应对象
        ServerHttpResponse response = exchange.getResponse();


        if(!IP_WHITE_LIST.contains(sourceAddress)){
            //响应状态码为403 Forbidden (禁止访问)
            response.setStatusCode(HttpStatus.FORBIDDEN);
            // 返回 Mono 对象，它并不包含响应参数，相当于告诉程序，请求处理完成了
            return response.setComplete();
        }
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        User invokeUser = null;
        try{
            // 3. 用户鉴权（判断 ak 、sk 是否合法）
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception error){
            log.error("getInvokeUser error" + error);
        }
        // 去查数据库，如果查不到相应密钥用户，就返回 false
        if(invokeUser == null){
            return handleNoAuth(response);
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过5分钟
        final Long FIVE_MINUTES = 5 * 60L;
        Long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        // 实际情况是从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = signUtils.genSign(body, secretKey);
        if(sign == null || !serverSign.equals(sign)){
            return handleNoAuth(response);
        }
        InterfaceInfo interfaceInfo = null;
        try{
//        4. 请求的模拟接口是否存在
            // 从数据库中间查询模拟接口是否存在，以及请求方法是否匹配(还可以校验请求参数)
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch(Exception e){
            log.error("getInterfaceInfo error" + e);
        }
        if(interfaceInfo == null) {
            return handleNoAuth(response);
        }
//        5. 请求转发，调用模拟接口 （先保证能调用接口，这个是最关键的，所以要优先实现）
//        Mono<Void> filter = chain.filter(exchange);
//        6. 响应日志
//        log.info("响应:" + response.getStatusCode());
        Long interfaceInfoId = interfaceInfo.getId();
        Long userId = invokeUser.getId();
        return handleResponse(exchange,chain,interfaceInfoId,userId);
    }


    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 用 try - catch 包裹住，防止因为调用方法时报错而导致整个网关挂了
                                try{
                                    // 调用成功，接口调用次数 + 1 invokeCount
                                    innerUserInterfaceInfoService.invokeCount(userId,interfaceInfoId);
                                }catch (Exception e){
                                    log.error("invokeCount error" + e);
                                }

                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info("响应结果:" + data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }



    @Override
    public int getOrder() {
        return -1;
    }
}