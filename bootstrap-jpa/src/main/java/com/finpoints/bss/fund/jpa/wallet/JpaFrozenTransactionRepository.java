package com.finpoints.bss.fund.jpa.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFrozenTransactionRepository extends JpaRepository<JpaFrozenFlow, Long> {

    JpaFrozenFlow findByFlowId(String flowId);

    JpaFrozenFlow findByIdemKey(String idemKey);

    boolean existsByFlowId(String flowId);
}
