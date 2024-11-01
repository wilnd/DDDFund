package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenTransaction;
import com.finpoints.bss.fund.domain.model.wallet.FrozenTransactionId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenTransactionRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaFrozenTransaction;
import com.finpoints.bss.fund.jpa.wallet.JpaFrozenTransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FrozenTransactionRepositoryImpl extends CrudRepositoryImpl<FrozenTransaction, FrozenTransactionId, JpaFrozenTransaction, Long>
        implements FrozenTransactionRepository {

    private final JpaFrozenTransactionRepository jpaFrozenTransactionRepository;

    public FrozenTransactionRepositoryImpl(JpaFrozenTransactionRepository jpaFrozenTransactionRepository) {
        super(new FrozenTransactionEntityConverter(), jpaFrozenTransactionRepository);
        this.jpaFrozenTransactionRepository = jpaFrozenTransactionRepository;
    }

    @Override
    public FrozenTransactionId nextId() {
        return new FrozenTransactionId(IdentityGenerator.nextIdentity());
    }

    @Override
    public FrozenTransaction findById(FrozenTransactionId id) {
        JpaFrozenTransaction transaction = jpaFrozenTransactionRepository.findByTransactionId(id.rawId());
        return convertToDomain(transaction);
    }

    @Override
    public boolean existsById(FrozenTransactionId id) {
        return jpaFrozenTransactionRepository.existsByTransactionId(id.rawId());
    }

    @Override
    public FrozenTransaction findByIdemKey(String idemKey) {
        JpaFrozenTransaction transaction = jpaFrozenTransactionRepository.findByIdemKey(idemKey);
        return convertToDomain(transaction);
    }

    public static class FrozenTransactionEntityConverter implements JpaEntityConverter<FrozenTransaction, JpaFrozenTransaction> {

        @Override
        public FrozenTransaction toDomainEntity(JpaFrozenTransaction persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new FrozenTransaction(
                    new FrozenTransactionId(persistenceEntity.getTransactionId()),
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
        public JpaFrozenTransaction toPersistenceEntity(FrozenTransaction domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaFrozenTransaction(
                    domainEntity.getTransactionId().rawId(),
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
