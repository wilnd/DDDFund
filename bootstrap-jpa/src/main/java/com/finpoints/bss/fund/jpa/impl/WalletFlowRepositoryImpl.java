package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlow;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowRepository;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletTransaction;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletTransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletFlowRepositoryImpl extends CrudRepositoryImpl<WalletFlow, WalletFlowId, JpaWalletTransaction, Long>
        implements WalletFlowRepository {

    private final JpaWalletTransactionRepository jpaWalletTransactionRepository;

    public WalletFlowRepositoryImpl(JpaWalletTransactionRepository jpaWalletTransactionRepository) {
        super(new JpaWalletTransactionEntityConverter(), jpaWalletTransactionRepository);
        this.jpaWalletTransactionRepository = jpaWalletTransactionRepository;
    }

    @Override
    public WalletFlowId nextId() {
        return new WalletFlowId(IdentityGenerator.nextIdentity());
    }

    @Override
    public WalletFlow findById(WalletFlowId id) {
        return null;
    }

    @Override
    public boolean existsById(WalletFlowId id) {
        return false;
    }


    public static class JpaWalletTransactionEntityConverter implements JpaEntityConverter<WalletFlow, JpaWalletTransaction> {

        @Override
        public WalletFlow toDomainEntity(JpaWalletTransaction persistenceEntity) {
            return null;
        }

        @Override
        public JpaWalletTransaction toPersistenceEntity(WalletFlow domainEntity) {
            return null;
        }
    }

}
