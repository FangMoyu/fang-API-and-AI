package com.fangapi.fangapiclientsdk.Utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class signUtils {
    public static String genSign(String body, String secretKey){
        // 使用SHA256算法的Digester
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        String content = body + "." + secretKey;
        // 计算签名的摘要并返回摘要的十六进制表示形式
        return md5.digestHex(content);
    }
}
