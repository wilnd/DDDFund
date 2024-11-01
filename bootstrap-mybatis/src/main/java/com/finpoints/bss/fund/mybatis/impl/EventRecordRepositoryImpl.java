package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.event.EventRecord;
import com.finpoints.bss.fund.domain.model.event.EventRecordId;
import com.finpoints.bss.fund.domain.model.event.EventRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class EventRecordRepositoryImpl implements EventRecordRepository {


    @Override
    public EventRecordId nextId() {
        return null;
    }

    @Override
    public EventRecord save(EventRecord entity) {
        return null;
    }

    @Override
    public Collection<EventRecord> saveAll(Collection<EventRecord> entities) {
        return List.of();
    }

    @Override
    public EventRecord findById(EventRecordId eventRecordId) {
        return null;
    }

    @Override
    public void delete(EventRecord entity) {

    }

    @Override
    public void deleteById(EventRecordId eventRecordId) {

    }

    @Override
    public boolean existsById(EventRecordId eventRecordId) {
        return false;
    }
}
