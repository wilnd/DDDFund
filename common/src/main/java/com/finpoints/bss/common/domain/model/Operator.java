package com.finpoints.bss.common.domain.model;

import lombok.Getter;

@Getter
public class Operator extends ValueObject {

    public static final String SYSTEM_ROLE = "SYSTEM";

    private final String role;
    private final String userId;
    private final String userName;

    public Operator(String role, String userId) {
        this.role = role;
        this.userId = userId;
        this.userName = null;
    }

    public Operator(String role, String userId, String userName) {
        this.role = role;
        this.userId = userId;
        this.userName = userName;
    }

    protected Operator() {
        this.role = null;
        this.userId = null;
        this.userName = null;
    }

    public static Operator current() {
        return system();
    }

    public static Operator of(String role, String userId) {
        return new Operator(role, userId, null);
    }

    public static Operator of(String role, String userId, String userName) {
        return new Operator(role, userId, userName);
    }

    public static Operator system() {
        return new Operator("SYSTEM", "system", "System");
    }
}
