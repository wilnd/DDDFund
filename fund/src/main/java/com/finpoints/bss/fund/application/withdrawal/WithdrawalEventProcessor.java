package com.finpoints.bss.fund.application.withdrawal;

import com.finpoints.bss.fund.domain.model.approval.ApprovalApproved;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRejected;
import com.finpoints.bss.fund.domain.model.wallet.WalletOperationService;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderRepository;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.locks.Lock;

@Slf4j
@Component
public class WithdrawalEventProcessor {

    private final WithdrawalService withdrawalService;
    private final WithdrawalOrderRepository withdrawalOrderRepository;

    private final WalletOperationService walletOperationService;

    public WithdrawalEventProcessor(WithdrawalService withdrawalService,
                                    WithdrawalOrderRepository withdrawalOrderRepository,
                                    WalletOperationService walletOperationService) {
        this.withdrawalService = withdrawalService;
        this.withdrawalOrderRepository = withdrawalOrderRepository;
        this.walletOperationService = walletOperationService;
    }

    /**
     * 处理出金风控审批通过事件
     */
    @ApplicationModuleListener(condition = "#event.type.name() == 'Withdrawal' and #event.role.name() == 'Risk'")
    public void processWithdrawalRiskApproved(ApprovalApproved event) {

        WithdrawalOrder order = withdrawalOrderRepository.findById(new WithdrawalOrderNo(event.getOrderNo()));
        if (order == null) {
            log.warn("Withdrawal order not found: {}", event.getOrderNo());
            return;
        }

        // 状态更改为审核中
        order.updateStatus(WithdrawalOrderStatus.APPROVING);
        withdrawalOrderRepository.save(order);

        log.info("Withdrawal order {} status APPROVING", order.getOrderNo());
    }

    /**
     * 处理出金风控/财务审批拒绝事件
     */
    @Async
    @TransactionalEventListener(condition = "#event.type.name() == 'Withdrawal' and " +
            "(#event.role.name() == 'Risk' or #event.role.name() == 'Finance')")
    public void processWithdrawalRejected(ApprovalRejected event) {

        WithdrawalOrder order = withdrawalOrderRepository.findById(new WithdrawalOrderNo(event.getOrderNo()));
        if (order == null) {
            log.warn("Withdrawal order not found: {}", event.getOrderNo());
            return;
        }

        // 加钱包锁
        Lock walletLock = walletOperationService.getWalletLock(order.getWalletId());
        boolean locked = false;
        try {
            // 尝试获取钱包锁
            locked = walletLock.tryLock();
            if (!locked) {
                throw new RuntimeException("Failed to lock wallet");
            }

            withdrawalService.innerCancelWithdrawal(order, WithdrawalOrderStatus.REJECTED);
            log.info("Withdrawal order {} status REJECTED", order.getOrderNo());
        } finally {
            if (locked) {
                walletLock.unlock();
            }
        }
    }
}
