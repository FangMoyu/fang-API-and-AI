package com.fang.fangapiinterface.Controller;
import com.fangapi.fangapiclientsdk.Model.User;
import com.fangapi.fangapiclientsdk.Utils.signUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(String name){
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        return "POST 用户名字是" + user.getUsername();
    }
}
