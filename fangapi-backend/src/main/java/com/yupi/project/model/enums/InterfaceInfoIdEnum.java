package com.yupi.project.model.enums;

public enum InterfaceInfoIdEnum {
    LOVE_STORY_ID(47L, "土味情话 Id"),
    ;


    private final Long id;
    private final String message;

    InterfaceInfoIdEnum(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
