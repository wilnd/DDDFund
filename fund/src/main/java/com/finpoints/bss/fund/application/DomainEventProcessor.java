package com.finpoints.bss.fund.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.fund.domain.model.event.EventRecord;
import com.finpoints.bss.fund.domain.model.event.EventRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventProcessor {

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
