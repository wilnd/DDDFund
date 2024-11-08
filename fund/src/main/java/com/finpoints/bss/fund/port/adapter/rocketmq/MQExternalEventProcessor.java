package com.finpoints.bss.fund.port.adapter.rocketmq;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.event.ComponentPathSerializer;
import com.finpoints.bss.common.event.ExternalEvent;
import com.finpoints.bss.common.event.ExternalEventConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.core.EventSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MQExternalEventProcessor {

    private final EventSerializer eventSerializer;
    private final RocketMQTemplate rocketMQTemplate;

    public MQExternalEventProcessor(ComponentPathSerializer eventSerializer,
                                    RocketMQTemplate rocketMQTemplate) {
        this.eventSerializer = eventSerializer;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @ApplicationModuleListener
    public void process(ExternalEvent event) {

        log.info("Processing external event {}, key: {}", event.getClass().getSimpleName(), event.key());

        String eventJson;
        ExternalEventConverter converter = event.converter();
        if (converter != null) {
            Object eventData = converter.convert(event);
            if (eventData instanceof String data) {
                eventJson = data;
            } else {
                eventJson = (String) eventSerializer.serialize(eventData);
            }
        } else {
            eventJson = (String) eventSerializer.serialize(event);
        }
        MessageBuilder<String> builder = MessageBuilder.withPayload(eventJson)
                .setHeader(MessageConst.PROPERTY_KEYS, event.key())
                .setHeader(MessageConst.PROPERTY_TAGS, event.tag());

        if (event instanceof DomainEvent domainEvent) {
            builder.setHeader("eventName", domainEvent.eventName())
                    .setHeader("eventType", domainEvent.eventType())
                    .setHeader("module", domainEvent.module().name());
        }

        rocketMQTemplate.send(event.topic(), builder.build());
        log.info("Sent external event {} to topic: {}, tag: {}, payload: {}",
                event.getClass().getSimpleName(), event.topic(), event.tag(), eventJson);
    }
}
