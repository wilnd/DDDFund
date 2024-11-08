package com.finpoints.bss.common.domain.model;

import java.util.ArrayList;
import java.util.List;

public class AggregateRoot extends Entity {

    private final transient List<DomainEvent> domainEvents = new ArrayList<>();

    protected AggregateRoot() {
    }

    protected AggregateRoot(String appId) {
        super(appId);
    }

    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }
}
