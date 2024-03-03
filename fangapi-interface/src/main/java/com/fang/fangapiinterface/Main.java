package com.fang.fangapiinterface;


import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import com.fangapi.fangapiclientsdk.Model.User;

public class Main {
    public static void main(String[] args) {
        String accessKey = "fangpi";
        String secretKey = "abcdefgh";
        fangApiClient yuApiClient = new fangApiClient(accessKey,secretKey);
        String result1 = yuApiClient.getNameByGet("鱼皮");
        String result2 = yuApiClient.getNameByPost("鱼皮");
        User user = new User();
        user.setUsername("adsab");
        String result3 = yuApiClient.getUserNameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
