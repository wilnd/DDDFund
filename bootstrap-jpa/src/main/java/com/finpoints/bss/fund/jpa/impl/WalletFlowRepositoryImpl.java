package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlow;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowRepository;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletFlow;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletFlowRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletFlowRepositoryImpl extends CrudRepositoryImpl<WalletFlow, WalletFlowId, JpaWalletFlow, Long>
        implements WalletFlowRepository {

    private final JpaWalletFlowRepository jpaWalletFlowRepository;

    public WalletFlowRepositoryImpl(JpaWalletFlowRepository jpaWalletFlowRepository) {
        super(new JpaWalletTransactionEntityConverter(), jpaWalletFlowRepository);
        this.jpaWalletFlowRepository = jpaWalletFlowRepository;
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


    public static class JpaWalletTransactionEntityConverter implements JpaEntityConverter<WalletFlow, JpaWalletFlow> {

        @Override
        public WalletFlow toDomainEntity(JpaWalletFlow persistenceEntity) {
            return null;
        }

        @Override
        public JpaWalletFlow toPersistenceEntity(WalletFlow domainEntity) {
            return null;
        }
    }

}
