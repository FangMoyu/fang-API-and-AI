package com.yupi.project.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户签名返回类
 */
@Data
public class UserAccessVO implements Serializable {

    /**
     * 用户 Id
     */
    private Long userId;

    /**
     * 签名
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;


    private static final long serialVersionUID = -6208053149522324764L;
}
