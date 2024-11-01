package com.finpoints.bss.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public abstract class Entity extends IdentifiedDomainObject {

    private String appId;

    @Setter
    private Integer version;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    protected Entity() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    public void setAppId(Long delegateId, String appId) {
        super.setId(delegateId);
        this.setAppId(appId);
    }

    public void setTime(LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.setCreatedTime(createdTime);
        this.setUpdatedTime(updatedTime);
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    private void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    private void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
