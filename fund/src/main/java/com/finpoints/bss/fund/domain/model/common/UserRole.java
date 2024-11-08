package com.finpoints.bss.fund.domain.model.common;

import lombok.Getter;

@Getter
public enum UserRole {

    IB("ib"),
    Client("client"),
    Admin("admin");

    private final String type;

    UserRole(String roleType) {
        this.type = roleType;
    }

    public static UserRole fromType(String type) {
        for (UserRole role : UserRole.values()) {
            if (role.getType().equals(type)) {
                return role;
            }
        }
        return null;
    }
}
