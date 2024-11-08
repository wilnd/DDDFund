package com.finpoints.bss.common.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class DomainEvent implements Serializable {

    private final transient String appId;
    private final transient LocalDateTime occurredOn;
    private final transient Operator operator;

    protected DomainEvent(String appId) {
        this.appId = appId;
        this.occurredOn = LocalDateTime.now();
        this.operator = Operator.current();
    }

    public String appId() {
        return appId;
    }

    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String eventName() {
        return this.getClass().getSimpleName();
    }

    public String eventType() {
        return this.getClass().getName();
    }

    public Operator operator() {
        return operator;
    }

    public abstract DomainEventModule module();
}
