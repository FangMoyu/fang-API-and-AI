package com.fangapi.fangapiclientsdk.Client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fangapi.fangapiclientsdk.Model.User;
import com.fangapi.fangapiclientsdk.Utils.signUtils;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用系统自带接口
 */
@AllArgsConstructor
public class fangApiClient {
    private String accessKey;
    private String secretKey;

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8090/api/name/", paramMap);
        return result;
    }

    public String getNameByPost(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name );
        String result = HttpUtil.post("http://localhost:8090/api/name/", paramMap);
        return result;
    }
    public Map<String,String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("body",body);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", signUtils.genSign(body,secretKey));
        return hashMap;
    }

    // 使用POST方法向服务器发送User对象，并获取服务器返回的结果
    public String getUserNameByPost(User user) {
        // 将User对象转换为JSON字符串
        String json = JSONUtil.toJsonStr(user);
        // 使用HttpRequest工具发起POST请求，并获取服务器的响应
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8090/api/name/user/")
                .body(json)
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

    /**
     * 获取随机土味情话
     * @return
     */
    public String getRandomLoveStory(Long interfaceInfoId) {
        // 没有参数，就随便混点盐进去
        String saltStr = RandomUtil.randomString(5);
        String json = JSONUtil.toJsonStr(saltStr);
        // 使用HttpRequest工具发起POST请求，并获取服务器的响应
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8090/api/ai/love/story/random/gen")
                .body(json)
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
}

