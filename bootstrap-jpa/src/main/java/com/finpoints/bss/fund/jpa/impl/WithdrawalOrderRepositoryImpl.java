package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.withdrawal.MTWithdrawalRequestId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderRepository;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.withdrawal.JpaWithdrawalOrder;
import com.finpoints.bss.fund.jpa.withdrawal.JpaWithdrawalOrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WithdrawalOrderRepositoryImpl extends CrudRepositoryImpl<WithdrawalOrder, WithdrawalOrderNo, JpaWithdrawalOrder, Long>
        implements WithdrawalOrderRepository {

    private final JpaWithdrawalOrderRepository jpaWithdrawalOrderRepository;


    public WithdrawalOrderRepositoryImpl(JpaWithdrawalOrderRepository jpaWithdrawalOrderRepository) {
        super(new WithdrawalOrderEntityConverter(), jpaWithdrawalOrderRepository);
        this.jpaWithdrawalOrderRepository = jpaWithdrawalOrderRepository;
    }

    @Override
    public WithdrawalOrderNo nextId() {
        return new WithdrawalOrderNo(IdentityGenerator.nextIdentity());
    }

    @Override
    public WithdrawalOrder findById(WithdrawalOrderNo orderNo) {
        JpaWithdrawalOrder order = jpaWithdrawalOrderRepository.findByOrderNo(orderNo.rawId());
        return convertToDomain(order);
    }

    @Override
    public boolean existsById(WithdrawalOrderNo id) {
        return jpaWithdrawalOrderRepository.existsByOrderNo(id.rawId());
    }

    @Override
    public WithdrawalOrder userWithdrawalOrder(UserId userId, WithdrawalOrderNo orderNo) {
        JpaWithdrawalOrder order = jpaWithdrawalOrderRepository.findByOrderNoAndUserId(orderNo.rawId(), userId.rawId());
        return convertToDomain(order);
    }

    public static class WithdrawalOrderEntityConverter implements JpaEntityConverter<WithdrawalOrder, JpaWithdrawalOrder> {
        @Override
        public WithdrawalOrder toDomainEntity(JpaWithdrawalOrder persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new WithdrawalOrder(
                    new WithdrawalOrderNo(persistenceEntity.getOrderNo()),
                    new UserId(persistenceEntity.getUserId()),
                    new WalletId(persistenceEntity.getWalletId()),
                    persistenceEntity.getWalletType(),
                    persistenceEntity.getWithdrawalMethod(),
                    persistenceEntity.getExchangeRate(),
                    persistenceEntity.getOriginalCurrency(),
                    persistenceEntity.getTargetCurrency(),
                    persistenceEntity.getAmount(),
                    persistenceEntity.getStatus(),
                    persistenceEntity.getMtRequestId() == null ? null
                            : new MTWithdrawalRequestId(persistenceEntity.getMtRequestId()),
                    persistenceEntity.getFrozenFlowId() == null ? null
                            : new FrozenFlowId(persistenceEntity.getFrozenFlowId())
            );
        }

        @Override
        public JpaWithdrawalOrder toPersistenceEntity(WithdrawalOrder domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaWithdrawalOrder(
                    domainEntity.getOrderNo().rawId(),
                    domainEntity.getUserId().rawId(),
                    domainEntity.getWalletId().rawId(),
                    domainEntity.getWalletType(),
                    domainEntity.getWithdrawalMethod(),
                    domainEntity.getExchangeRate(),
                    domainEntity.getOriginalCurrency(),
                    domainEntity.getTargetCurrency(),
                    domainEntity.getAmount(),
                    domainEntity.getStatus(),
                    domainEntity.getMtRequestId() == null ? null : domainEntity.getMtRequestId().rawId(),
                    domainEntity.getFrozenFlowId() == null ? null : domainEntity.getFrozenFlowId().rawId()
            );
        }
    }
}
