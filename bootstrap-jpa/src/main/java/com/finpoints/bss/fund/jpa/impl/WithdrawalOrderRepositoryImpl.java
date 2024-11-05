package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderRepository;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.withdrawal.JpaWithdrawalOrder;
import com.finpoints.bss.fund.jpa.withdrawal.JpaWithdrawalOrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WithdrawalOrderRepositoryImpl extends CrudRepositoryImpl<WithdrawalOrder, WithdrawalOrderNo, JpaWithdrawalOrder>
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
                    persistenceEntity.getRequestTime(),
                    persistenceEntity.getExchangeRate(),
                    persistenceEntity.getOriginalCurrency(),
                    persistenceEntity.getTargetCurrency(),
                    persistenceEntity.getAmount(),
                    persistenceEntity.getArrivalAmount(),
                    persistenceEntity.getServiceCharge(),
                    persistenceEntity.getBankId() == null ? null : new BankId(persistenceEntity.getBankId()),
                    persistenceEntity.getBankAccount(),
                    persistenceEntity.getBankInfo(),
                    persistenceEntity.getIntermediaryBankId() == null ? null : new BankId(persistenceEntity.getIntermediaryBankId()),
                    persistenceEntity.getIntermediaryBankAccount(),
                    persistenceEntity.getIntermediaryBankInfo(),
                    persistenceEntity.getStatus(),
                    persistenceEntity.getMtRequestId() == null ? null : new MtRequestId(persistenceEntity.getMtRequestId()),
                    persistenceEntity.getFrozenFlowId() == null ? null : new FrozenFlowId(persistenceEntity.getFrozenFlowId()),
                    persistenceEntity.getRecallTime()
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
                    domainEntity.getRequestTime(),
                    domainEntity.getExchangeRate(),
                    domainEntity.getOriginalCurrency(),
                    domainEntity.getTargetCurrency(),
                    domainEntity.getAmount(),
                    domainEntity.getArrivalAmount(),
                    domainEntity.getServiceCharge(),
                    domainEntity.getBankId() == null ? null : domainEntity.getBankId().rawId(),
                    domainEntity.getBankAccount(),
                    domainEntity.getBankInfo(),
                    domainEntity.getIntermediaryBankId() == null ? null : domainEntity.getIntermediaryBankId().rawId(),
                    domainEntity.getIntermediaryBankAccount(),
                    domainEntity.getIntermediaryBankInfo(),
                    domainEntity.getStatus(),
                    domainEntity.getMtRequestId() == null ? null : domainEntity.getMtRequestId().rawId(),
                    domainEntity.getFrozenFlowId() == null ? null : domainEntity.getFrozenFlowId().rawId(),
                    domainEntity.getRecallTime()
            );
        }
    }
}
