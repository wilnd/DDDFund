package com.finpoints.bss.fund.jpa.mt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMtRequestRepository extends JpaRepository<JpaMtRequest, Long> {

    JpaMtRequest findByRequestId(String requestId);

    boolean existsByRequestId(String requestId);
}
