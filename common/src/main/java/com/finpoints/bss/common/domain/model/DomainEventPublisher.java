package com.finpoints.bss.common.domain.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DomainEventPublisher implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private final ApplicationEventPublisher publisher;

    public DomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public static DomainEventPublisher instance() {
        return applicationContext.getBean(DomainEventPublisher.class);
    }

    public void publish(DomainEvent event) {
        this.publisher.publishEvent(event);
    }

    public void publishAll(Collection<DomainEvent> domainEvents) {
        domainEvents.forEach(this::publish);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DomainEventPublisher.applicationContext = applicationContext;
    }
}
