package com.yupi.project.model.dto.loveStory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yupi.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author yupi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoveStoryAddRequest extends PageRequest implements Serializable {
    /**
     * 情话内容
     */
    private String content;


    private static final long serialVersionUID = 1L;
}