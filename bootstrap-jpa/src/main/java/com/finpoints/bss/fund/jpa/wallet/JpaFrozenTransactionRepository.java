package com.finpoints.bss.fund.jpa.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFrozenTransactionRepository extends JpaRepository<JpaFrozenTransaction, Long> {

    JpaFrozenTransaction findByTransactionId(String transactionId);

    JpaFrozenTransaction findByIdemKey(String idemKey);

    boolean existsByTransactionId(String transactionId);
}
