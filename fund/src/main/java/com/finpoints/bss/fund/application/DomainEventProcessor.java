package com.finpoints.bss.fund.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.fund.domain.model.event.EventRecord;
import com.finpoints.bss.fund.domain.model.event.EventRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DomainEventProcessor {

    private static final Logger log = LoggerFactory.getLogger(DomainEventProcessor.class);
    private final ObjectMapper objectMapper;
    private final EventRecordRepository eventRecordRepository;

    public DomainEventProcessor(ObjectMapper objectMapper,
                                EventRecordRepository eventRecordRepository) {
        this.objectMapper = objectMapper;
        this.eventRecordRepository = eventRecordRepository;
    }


    @ApplicationModuleListener
    public void process(DomainEvent event) {

        String serializedEvent;
        try {
            serializedEvent = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event {}", event.eventName(), e);
            return;
        }

        EventRecord eventRecord = new EventRecord(
                eventRecordRepository.nextId(),
                event.occurredOn(),
                event.eventName(),
                event.eventType(),
                event.module(),
                serializedEvent,
                event.operator()
        );
        eventRecordRepository.save(eventRecord);
        log.info("Domain Event recorded: {}", event.eventName());
    }
}
