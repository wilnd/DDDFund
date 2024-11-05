package com.finpoints.bss.fund.application;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.fund.domain.model.event.EventRecord;
import com.finpoints.bss.fund.domain.model.event.EventRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.core.EventSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventProcessor {

    private final EventSerializer eventSerializer;
    private final EventRecordRepository eventRecordRepository;

    public DomainEventProcessor(EventSerializer eventSerializer,
                                EventRecordRepository eventRecordRepository) {
        this.eventSerializer = eventSerializer;
        this.eventRecordRepository = eventRecordRepository;
    }


    @ApplicationModuleListener
    public void process(DomainEvent event) {
        String serializedEvent = (String) eventSerializer.serialize(event);
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
