package com.finpoints.bss.common.requester;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class Requester extends ValueObject {

    private final String appId;
    private final String role;
    private final String userId;
    private final String username;
    private final String ip;

    public Requester(String appId, String role, String userId, String username, String ip) {
        this.appId = appId;
        this.role = role;
        this.userId = userId;
        this.username = username;
        this.ip = ip;
    }
}
