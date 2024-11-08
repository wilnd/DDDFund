package com.finpoints.bss.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Entity implements IdentifiedDomainObject {

    /**
     * 用于存储在数据库中的主键ID，一般用于提升DB性能，不参与业务
     */
    private String delegateId;
    private String appId;

    @Setter
    private Integer version;
    private Instant createdTime;
    private Instant updatedTime;

    /**
     * 扩展属性，用于适配层存储额外非业务信息
     */
    private final Map<String, Object> extraAttributes = new HashMap<>();

    protected Entity() {
    }

    protected Entity(String appId) {
        this.appId = appId;
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

    public void addAttribute(String key, Object value) {
        extraAttributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return extraAttributes.get(key);
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
