package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlow;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletFlow;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletFlowRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletFlowRepositoryImpl extends CrudRepositoryImpl<WalletFlow, WalletFlowId, JpaWalletFlow>
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
        JpaWalletFlow walletFlow = jpaWalletFlowRepository.findByFlowId(id.rawId());
        return convertToDomain(walletFlow);
    }

    @Override
    public boolean existsById(WalletFlowId id) {
        return jpaWalletFlowRepository.existsByFlowId(id.rawId());
    }


    public static class JpaWalletTransactionEntityConverter implements JpaEntityConverter<WalletFlow, JpaWalletFlow> {

        @Override
        public WalletFlow toDomainEntity(JpaWalletFlow persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new WalletFlow(
                    new WalletFlowId(persistenceEntity.getFlowId()),
                    persistenceEntity.getWalletType(),
                    new WalletId(persistenceEntity.getWalletId()),
                    persistenceEntity.getUserRole(),
                    new UserId(persistenceEntity.getUserId()),
                    persistenceEntity.getFlowType(),
                    persistenceEntity.getBusinessOrderNo(),
                    persistenceEntity.getCurrency(),
                    persistenceEntity.getOriginalBalance(),
                    persistenceEntity.getBalanceChange(),
                    persistenceEntity.getExistingBalance(),
                    persistenceEntity.getRemark(),
                    persistenceEntity.getOperator()
            );
        }

        @Override
        public JpaWalletFlow toPersistenceEntity(WalletFlow domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaWalletFlow(
                    domainEntity.getFlowId().rawId(),
                    domainEntity.getWalletType(),
                    domainEntity.getWalletId().rawId(),
                    domainEntity.getUserRole(),
                    domainEntity.getUserId().rawId(),
                    domainEntity.getFlowType(),
                    domainEntity.getBusinessOrderNo(),
                    domainEntity.getCurrency(),
                    domainEntity.getOriginalBalance(),
                    domainEntity.getBalanceChange(),
                    domainEntity.getExistingBalance(),
                    domainEntity.getRemark(),
                    domainEntity.getOperator()
            );
        }
    }

}
