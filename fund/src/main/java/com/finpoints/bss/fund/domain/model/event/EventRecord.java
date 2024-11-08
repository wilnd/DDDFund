package com.finpoints.bss.fund.domain.model.event;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import com.finpoints.bss.common.domain.model.Operator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventRecord extends AggregateRoot {

    /**
     * 事件ID
     */
    private final EventRecordId eventId;

    /**
     * 事件发生时间
     */
    private final LocalDateTime occurredOn;

    /**
     * 事件名称
     */
    private final String eventName;

    /**
     * 事件类型
     */
    private final String eventType;

    /**
     * 事件所属模块
     */
    private final DomainEventModule module;

    /**
     * 事件序列化内容
     */
    private final String serializedEvent;

    /**
     * 操作人
     */
    private final Operator operator;

    public EventRecord(String appId, EventRecordId eventId, LocalDateTime occurredOn, String eventName, String eventType,
                       DomainEventModule module, String serializedEvent, Operator operator) {
        super(appId);

        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.eventName = eventName;
        this.eventType = eventType;
        this.module = module;
        this.serializedEvent = serializedEvent;
        this.operator = operator;
    }
}
