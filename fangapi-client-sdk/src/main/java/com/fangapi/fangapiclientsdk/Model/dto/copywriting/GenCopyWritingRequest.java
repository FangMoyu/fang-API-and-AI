package com.fangapi.fangapiclientsdk.Model.dto.copywriting;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 生成文案请求
 */
@Data
@AllArgsConstructor
public class GenCopyWritingRequest {
    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户输入
     */
    private String userInput;
}
