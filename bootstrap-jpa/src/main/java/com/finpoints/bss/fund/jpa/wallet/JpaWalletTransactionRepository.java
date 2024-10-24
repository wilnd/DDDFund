package com.finpoints.bss.fund.jpa.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWalletTransactionRepository extends JpaRepository<JpaWalletTransaction, Long> {
}
