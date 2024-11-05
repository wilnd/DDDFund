package com.finpoints.bss.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public abstract class Entity implements IdentifiedDomainObject {

    private String delegateId;
    private String appId;

    @Setter
    private Integer version;
    private Instant createdTime;
    private Instant updatedTime;

    protected Entity() {
        this.createdTime = Instant.now();
        this.updatedTime = Instant.now();
    }

    @Override
    public String delegateId() {
        return delegateId;
    }

    public void setAppId(String delegateId, String appId) {
        this.delegateId = delegateId;
        this.setAppId(appId);
    }

    public void setTime(Instant createdTime, Instant updatedTime) {
        this.setCreatedTime(createdTime);
        this.setUpdatedTime(updatedTime);
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    private void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    private void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }
}
