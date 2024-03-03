package com.yupi.project.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoQueryRequest implements Serializable{
    /**
     * 根据 id 查询
     */
    private Long id;
    /**
     * 根据用户 id 查询
     */
    private Long userId;

    /**
     * 根据接口 id 查询
     */
    private Long interfaceInfoId;

    /**
     * 可以通过调用次数范围查询
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     * 根据状态查询
     */
    private Integer status;
}
