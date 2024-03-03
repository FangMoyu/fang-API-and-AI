package com.yupi.project.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;



@Data
public class InterfaceInfoAddRequest {
    /**
     * 名称
     */
    private String name;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 用户名
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;


    /**
     * 请求方法类型
     */
    private String method;


}
