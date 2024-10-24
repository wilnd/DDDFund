package com.finpoints.bss.fund.jpa.event;

import com.finpoints.bss.common.domain.model.DomainEventModule;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "event_record")
@AllArgsConstructor
public class JpaEventRecord extends JpaEntityBase {

    @Comment("事件ID")
    @Column(length = 64, unique = true)
    private String eventId;

    @Comment("发生时间")
    private LocalDateTime occurredOn;

    @Comment("事件名称")
    private String eventName;

    @Comment("事件类型")
    private String eventType;

    @Comment("所属模块")
    @Enumerated(EnumType.STRING)
    private DomainEventModule module;

    @Comment("序列化事件")
    @Column(length = 4096)
    private String serializedEvent;

    @Embedded
    @Comment("操作人")
    private Operator operator;

    protected JpaEventRecord() {
    }
}
