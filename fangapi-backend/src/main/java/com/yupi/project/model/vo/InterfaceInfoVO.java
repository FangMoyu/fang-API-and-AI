package com.yupi.project.model.vo;

import com.fang.fangapicommon.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;
@Data
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {
    private static final long serialVersionUID = -7297792734728222295L;
    /**
     * 调用次数
     */
    private Integer totalNum;

}
