package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.event.EventRecord;
import com.finpoints.bss.fund.domain.model.event.EventRecordId;
import com.finpoints.bss.fund.domain.model.event.EventRecordRepository;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.event.JpaEventRecord;
import com.finpoints.bss.fund.jpa.event.JpaEventRecordRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EventRecordRepositoryImpl extends CrudRepositoryImpl<EventRecord, EventRecordId, JpaEventRecord, Long>
        implements EventRecordRepository {

    private final JpaEventRecordRepository jpaEventRecordRepository;

    public EventRecordRepositoryImpl(JpaEventRecordRepository jpaEventRecordRepository) {
        super(new EventRecordEntityConverter(), jpaEventRecordRepository);
        this.jpaEventRecordRepository = jpaEventRecordRepository;
    }

    @Override
    public EventRecordId nextId() {
        return new EventRecordId(IdentityGenerator.nextIdentity());
    }

    @Override
    public EventRecord findById(EventRecordId eventRecordId) {
        JpaEventRecord eventRecord = jpaEventRecordRepository.findByEventId(eventRecordId.rawId());
        return convertToDomain(eventRecord);
    }

    @Override
    public boolean existsById(EventRecordId eventRecordId) {
        return jpaEventRecordRepository.existsByEventId(eventRecordId.rawId());
    }

    public static class EventRecordEntityConverter implements JpaEntityConverter<EventRecord, JpaEventRecord> {

        @Override
        public EventRecord toDomainEntity(JpaEventRecord persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }

            return new EventRecord(
                    new EventRecordId(persistenceEntity.getEventId()),
                    persistenceEntity.getOccurredOn(),
                    persistenceEntity.getEventName(),
                    persistenceEntity.getEventType(),
                    persistenceEntity.getModule(),
                    persistenceEntity.getSerializedEvent(),
                    persistenceEntity.getOperator()
            );
        }

        @Override
        public JpaEventRecord toPersistenceEntity(EventRecord domainEntity) {
            if (domainEntity == null) {
                return null;
            }

            return new JpaEventRecord(
                    domainEntity.getEventId().rawId(),
                    domainEntity.getOccurredOn(),
                    domainEntity.getEventName(),
                    domainEntity.getEventType(),
                    domainEntity.getModule(),
                    domainEntity.getSerializedEvent(),
                    domainEntity.getOperator()
            );
        }
    }
}
