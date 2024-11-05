package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenFlow;
import com.finpoints.bss.fund.domain.model.wallet.FrozenFlowId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenFlowRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaFrozenFlow;
import com.finpoints.bss.fund.jpa.wallet.JpaFrozenFlowRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FrozenFlowRepositoryImpl extends CrudRepositoryImpl<FrozenFlow, FrozenFlowId, JpaFrozenFlow>
        implements FrozenFlowRepository {

    private final JpaFrozenFlowRepository jpaFrozenFlowRepository;

    public FrozenFlowRepositoryImpl(JpaFrozenFlowRepository jpaFrozenFlowRepository) {
        super(new FrozenTransactionEntityConverter(), jpaFrozenFlowRepository);
        this.jpaFrozenFlowRepository = jpaFrozenFlowRepository;
    }

    @Override
    public FrozenFlowId nextId() {
        return new FrozenFlowId(IdentityGenerator.nextIdentity());
    }

    @Override
    public FrozenFlow findById(FrozenFlowId id) {
        JpaFrozenFlow transaction = jpaFrozenFlowRepository.findByFlowId(id.rawId());
        return convertToDomain(transaction);
    }

    @Override
    public boolean existsById(FrozenFlowId id) {
        return jpaFrozenFlowRepository.existsByFlowId(id.rawId());
    }

    @Override
    public FrozenFlow findByIdemKey(String idemKey) {
        JpaFrozenFlow transaction = jpaFrozenFlowRepository.findByIdemKey(idemKey);
        return convertToDomain(transaction);
    }

    public static class FrozenTransactionEntityConverter implements JpaEntityConverter<FrozenFlow, JpaFrozenFlow> {

        @Override
        public FrozenFlow toDomainEntity(JpaFrozenFlow persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new FrozenFlow(
                    new FrozenFlowId(persistenceEntity.getFlowId()),
                    persistenceEntity.getIdemKey(),
                    new WalletId(persistenceEntity.getWalletId()),
                    new UserId(persistenceEntity.getUserId()),
                    persistenceEntity.getCurrency(),
                    persistenceEntity.getFreezeType(),
                    persistenceEntity.getAmount(),
                    persistenceEntity.getStatus(),
                    persistenceEntity.getRemark(),
                    persistenceEntity.getUnfreezeType()
            );
        }

        @Override
        public JpaFrozenFlow toPersistenceEntity(FrozenFlow domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaFrozenFlow(
                    domainEntity.getFlowId().rawId(),
                    domainEntity.getIdemKey(),
                    domainEntity.getWalletId().rawId(),
                    domainEntity.getUserId().rawId(),
                    domainEntity.getCurrency(),
                    domainEntity.getFreezeType(),
                    domainEntity.getAmount(),
                    domainEntity.getStatus(),
                    domainEntity.getRemark(),
                    domainEntity.getUnfreezeType()
            );
        }
    }
}
