package com.fang.fangapiinterface;

import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import com.fangapi.fangapiclientsdk.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class FangapiInterfaceApplicationTests {

    @Resource
    private fangApiClient fangApiClient;

    @Test
    void contextLoads() {
        // 调用yuApiClient的getNameByGet方法，并传入参数"yupi"，将返回的结果赋值给result变量
        String result = fangApiClient.getNameByGet("yupi");
        // 创建一个User对象
        User user = new User();
        // 设置User对象的username属性为"liyupi"
        user.setUsername("liyupi");
        // 调用yuApiClient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = fangApiClient.getUserNameByPost(user);
        // 打印result变量的值
        System.out.println(result);
        // 打印usernameByPost变量的值
        System.out.println(usernameByPost);
    }

}
