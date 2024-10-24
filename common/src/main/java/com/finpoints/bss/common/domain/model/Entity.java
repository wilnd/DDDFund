package com.finpoints.bss.common.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Entity extends IdentifiedDomainObject {

    private String appId;

    private Integer version;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    protected Entity() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    public void setAppIdAndVersion(Long delegateId, String appId, Integer version) {
        super.setId(delegateId);
        this.setAppId(appId);
        this.setVersion(version);
    }

    public void setTime(LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.setCreatedTime(createdTime);
        this.setUpdatedTime(updatedTime);
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    private void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    private void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }


}
