package com.seven.common.core.enums;

import lombok.Getter;

@Getter
public enum UserIdentity {

    ORDINARY(1, "普通用户"),

    ADMIN(2, "管理员");

    private Integer value;
    private String des;

    UserIdentity(int value, String des) {
        this.value = value;
        this.des = des;
    }
}
