package com.finpoints.bss.fund.jpa.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWalletFlowRepository extends JpaRepository<JpaWalletFlow, Long> {

    JpaWalletFlow findByFlowId(String flowId);

    boolean existsByFlowId(String flowId);
}
