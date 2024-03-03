package com.yupi.project.model.dto.loveStory;

import com.yupi.project.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class LoveStoryQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     * 可以根据 id 直接查询
     */
    private Long id;

    /**
     * 情话内容
     * 可以根据内容模糊查询
     */
    private String content;



    private static final long serialVersionUID = 1L;
}