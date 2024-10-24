package com.finpoints.bss.fund.jpa.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEventRecordRepository extends JpaRepository<JpaEventRecord, Long> {

    JpaEventRecord findByEventId(String eventId);

    boolean existsByEventId(String eventId);
}
