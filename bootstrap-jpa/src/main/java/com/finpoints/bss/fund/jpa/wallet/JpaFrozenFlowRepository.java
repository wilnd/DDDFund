package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.fund.domain.model.wallet.FrozenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFrozenFlowRepository extends JpaRepository<JpaFrozenFlow, Long> {

    JpaFrozenFlow findByFlowId(String flowId);

    boolean existsByFlowId(String flowId);

    JpaFrozenFlow findByWalletIdAndFreezeTypeAndBusinessOrderNo(String walletId, FrozenType freezeType, String businessOrderNo);
}
