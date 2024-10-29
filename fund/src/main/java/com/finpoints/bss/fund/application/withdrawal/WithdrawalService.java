package com.finpoints.bss.fund.application.withdrawal;

import com.finpoints.bss.fund.application.withdrawal.command.ApplyWithdrawalCommand;
import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletOperationService;
import com.finpoints.bss.fund.domain.model.withdrawal.*;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;

@Service
public class WithdrawalService implements ClientWithdrawalService {

    private static final Logger log = LoggerFactory.getLogger(WithdrawalService.class);
    private final WithdrawalService _this;

    private final WalletOperationService walletOperationService;
    private final WithdrawalStrategyFactory strategyFactory;

    private final WithdrawalOrderRepository withdrawalOrderRepository;

    public WithdrawalService(@Lazy WithdrawalService aThis,
                             WalletOperationService walletOperationService,
                             WithdrawalStrategyFactory strategyFactory,
                             WithdrawalOrderRepository withdrawalOrderRepository) {
        _this = aThis;
        this.walletOperationService = walletOperationService;
        this.strategyFactory = strategyFactory;
        this.withdrawalOrderRepository = withdrawalOrderRepository;
    }

    @Override
    public WithdrawalOrderNo applyWithdrawal(ApplyWithdrawalCommand command) {
        Validate.notBlank(command.getUserId(), "User ID must not be null");
        Validate.notBlank(command.getWalletId(), "Wallet ID must not be blank");
        Validate.notNull(command.getWalletType(), "Wallet type must not be null");
        Validate.notNull(command.getWithdrawalMethod(), "Withdrawal method must not be null");
        Validate.notNull(command.getAmount(), "Amount must not be null");

        WalletId walletId = new WalletId(command.getWalletId());
        Lock walletLock = walletOperationService.getWalletLock(walletId);

        boolean locked = false;
        try {
            // 尝试获取钱包锁
            locked = walletLock.tryLock();
            if (!locked) {
                throw new RuntimeException("Failed to lock wallet");
            }

            WithdrawalOrder order = _this.innerSubmitWithdrawal(command);
            log.info("Withdrawal order submitted: {}", order.getOrderNo());

            return order.getOrderNo();
        } finally {
            if (locked) {
                walletLock.unlock();
            }
        }
    }

    @Transactional
    public WithdrawalOrder innerSubmitWithdrawal(ApplyWithdrawalCommand command) {

        // 检查出金订单是否已存在
        WithdrawalOrderNo orderNo = command.getOrderNo() == null ? null : new WithdrawalOrderNo(command.getOrderNo());
        if (orderNo != null && withdrawalOrderRepository.existsById(orderNo)) {
            throw new RuntimeException("Withdrawal order already exists");
        }

        // 出金策略
        WalletWithdrawalStrategy walletStrategy = strategyFactory.walletStrategy(command.getWalletType());
        WithdrawalMethodStrategy methodStrategy = strategyFactory.methodStrategy(command.getWithdrawalMethod());

        UserId userId = new UserId(command.getUserId());
        WalletId walletId = new WalletId(command.getWalletId());
        if (!walletStrategy.satisfied(walletId, command.getMtAccount(), command.getServerId(), command.getAmount())) {
            throw new RuntimeException("Wallet withdrawal strategy not satisfied");
        }

        Currency originCurrency = walletStrategy.getCurrency(walletId, command.getMtAccount(), command.getServerId());
        WithdrawalOrder withdrawalOrder = methodStrategy.withdrawal(
                userId, walletId, command.getWalletType(),
                command.getWithdrawalMethod(),
                command.getAmount(),
                originCurrency,
                command.getRemark(),
                command.getBankId() == null ? null : new BankId(command.getBankId()),
                command.getBankCardId() == null ? null : new BankCardId(command.getBankCardId()),
                orderNo == null ? withdrawalOrderRepository.nextId() : orderNo,
                command.getMtAccount(),
                command.getServerId()
        );
        withdrawalOrder.submit(walletOperationService);
        return withdrawalOrderRepository.save(withdrawalOrder);
    }

    @Override
    public WithdrawalOrder cancelWithdrawal(UserId userId, String withdrawalOrderNo) {

        Validate.notNull(userId, "User ID must not be blank");
        Validate.notBlank(withdrawalOrderNo, "Withdrawal order number must not be blank");

        WithdrawalOrderNo orderNo = new WithdrawalOrderNo(withdrawalOrderNo);

        WithdrawalOrder order = withdrawalOrderRepository.userWithdrawalOrder(userId, orderNo);
        if (order == null) {
            throw new RuntimeException("Withdrawal order not found");
        }

        Lock walletLock = walletOperationService.getWalletLock(order.getWalletId());
        boolean locked = false;
        try {
            // 尝试获取钱包锁
            locked = walletLock.tryLock();
            if (!locked) {
                throw new RuntimeException("Failed to lock wallet");
            }

            order = _this.innerCancelWithdrawal(order, WithdrawalOrderStatus.CANCELLED);
            log.info("Withdrawal order cancelled: {}", order.getOrderNo());
            return order;
        } finally {
            if (locked) {
                walletLock.unlock();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WithdrawalOrder innerCancelWithdrawal(WithdrawalOrder order, WithdrawalOrderStatus status) {
        order.cancel(status, walletOperationService);
        return withdrawalOrderRepository.save(order);
    }
}
