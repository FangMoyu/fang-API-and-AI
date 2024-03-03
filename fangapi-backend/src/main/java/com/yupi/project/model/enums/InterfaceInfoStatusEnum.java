package com.yupi.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InterfaceInfoStatusEnum {
    ONLINE("上线",1),
    OFFLINE("下线",0);
    /**
     * 描述
     */
    private final String text;

    private final int value;

    InterfaceInfoStatusEnum(String text, int status) {
        this.text = text;
        this.value = status;
    }
    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
