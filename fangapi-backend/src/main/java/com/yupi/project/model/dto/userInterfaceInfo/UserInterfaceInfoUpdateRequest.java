package com.yupi.project.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    private Long id;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
