package com.fangapi.fangapiclientsdk.Client;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.fangapi.fangapiclientsdk.Model.dto.chart.GenChartByAiRequest;
import com.fangapi.fangapiclientsdk.Model.dto.copywriting.GenCopyWritingRequest;
import com.fangapi.fangapiclientsdk.Utils.signUtils;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用 AI 接口 sdk
 */
@AllArgsConstructor
public class fangAiClient {
    private String accessKey;
    private String secretKey;

    private Map<String,String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        try {
            body = URLEncoder.encode(body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        hashMap.put("body",body);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", signUtils.genSign(body,secretKey));
        return hashMap;
    }

    /**
     *
     * @param
     * @param file
     * @param genChartByAiRequest
     * @return
     */
    public String UsingBIGenChart(File file,
                                  GenChartByAiRequest genChartByAiRequest) throws IOException {
        // 外部必须确保参数校验，避免 genChartByAiRequest 为空
        // 图表类型
        String chartType = genChartByAiRequest.getChartType();
        // 用户id
        Long userId = genChartByAiRequest.getUserId();
        // 目标
        String goal = genChartByAiRequest.getGoal();
        // 图表名称
        String name = genChartByAiRequest.getName();
        // 将User对象转换为JSON字符串
        String json = JSONUtil.toJsonStr(genChartByAiRequest);
        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file",file);
        paramMap.put("chartType", chartType);
        paramMap.put("goal", goal);
        paramMap.put("name", name);
        paramMap.put("userId", userId);
        // 使用HttpRequest工具发起POST请求，并获取服务器的响应
        HttpResponse httpResponse = HttpRequest
                .post("http://localhost:8090/api/ai/chart/gen/async/mq")
                .form(paramMap)
                //将整个 json 作为请求头，去混入到 API 签名认证中。
                .addHeaders(getHeaderMap(json))
                .execute(); // 执行请求
        // 打印服务器返回的状态码
        System.out.println(httpResponse.getStatus());
        // 获取服务器返回的结果
        String result = httpResponse.body();
        // 打印服务器返回的结果
        System.out.println(result);
        // 返回服务器返回的结果
        return result;
    }

    public String GenCopyWritingByAi(GenCopyWritingRequest genCopyWritingRequest) {
        Long userId = genCopyWritingRequest.getUserId();
        String userInput = genCopyWritingRequest.getUserInput();
        // 调用服务端接口生成文案
        HashMap<String , Object> hashMap = new HashMap<>();
        // 提供 SDK 的时候，顺便给用户显示 userId
        hashMap.put("userId",userId);
        hashMap.put("userInput",userInput);
        String json = JSONUtil.toJsonStr(userId + userInput);
        HttpResponse httpResponse = HttpRequest
                .post("http://localhost:8090/api/ai/copyWriting/gen")
                .form(hashMap)
                .addHeaders(getHeaderMap(json))
                .execute();
        // 打印服务器返回的状态码
        System.out.println(httpResponse.getStatus());
        // 获取服务器返回的结果
        String result = httpResponse.body();
        // 返回服务器返回的结果
        return result;
    }
}
