package com.finpoints.bss.fund.application.withdrawal;

import com.finpoints.bss.common.lock.LockTemplate;
import com.finpoints.bss.fund.application.withdrawal.command.ApplyWithdrawalCommand;
import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtServerId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletOperationService;
import com.finpoints.bss.fund.domain.model.withdrawal.*;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.locks.Lock;

@Service
public class WithdrawalService implements ClientWithdrawalService {

    private static final Logger log = LoggerFactory.getLogger(WithdrawalService.class);
    private final TransactionTemplate transactionTemplate;

    private final WalletOperationService walletOperationService;
    private final WithdrawalStrategyFactory strategyFactory;

    private final WithdrawalOrderRepository withdrawalOrderRepository;


    public WithdrawalService(TransactionTemplate transactionTemplate,
                             WalletOperationService walletOperationService,
                             WithdrawalStrategyFactory strategyFactory,
                             WithdrawalOrderRepository withdrawalOrderRepository) {
        this.transactionTemplate = transactionTemplate;
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

        UserId userId = new UserId(command.getUserId());
        WalletId walletId = new WalletId(command.getWalletId());

        // 获取钱包锁
        Lock walletLock = walletOperationService.getWalletLock(walletId);
        return LockTemplate.execute(walletLock, () -> {
            // 检查出金订单是否已存在
            WithdrawalOrderNo orderNo = command.getOrderNo() == null ? null : new WithdrawalOrderNo(command.getOrderNo());
            if (orderNo != null && withdrawalOrderRepository.existsById(orderNo)) {
                throw new IllegalArgumentException("Withdrawal order already exists");
            }

            // 出金策略
            WalletWithdrawalStrategy walletStrategy = strategyFactory.walletStrategy(command.getWalletType());
            WithdrawalMethodStrategy methodStrategy = strategyFactory.methodStrategy(command.getWithdrawalMethod());

            // 检查出金策略是否满足
            if (!walletStrategy.satisfied(walletId, command.getMtAccount(), command.getServerId(), command.getAmount())) {
                throw new IllegalArgumentException("Wallet withdrawal strategy not satisfied");
            }
            Currency originCurrency = walletStrategy.getCurrency(walletId, command.getMtAccount(), command.getServerId());

            // 开启事务，提交出金订单
            return transactionTemplate.execute(txStatus -> {
                WithdrawalOrder withdrawalOrder = methodStrategy.withdrawal(
                        command.getAppId(),
                        orderNo == null ? withdrawalOrderRepository.nextId() : orderNo,
                        userId, walletId, command.getWalletType(),
                        command.getRequestTime(),
                        command.getRemark(),
                        command.getAmount(),
                        originCurrency,
                        command.getBankId() == null ? null : new BankId(command.getBankId()),
                        command.getBankCardId() == null ? null : new BankCardId(command.getBankCardId()),
                        command.getServerId() == null ? null : new MtServerId(command.getServerId()),
                        command.getMtAccount()
                );

                // 非MT出金，直接提交出金
                if (withdrawalOrder.getMtRequestId() == null) {
                    log.info("Submit withdrawal order: {}", withdrawalOrder.getOrderNo());
                    withdrawalOrder.submit(walletOperationService);
                }
                withdrawalOrderRepository.save(withdrawalOrder);
                log.info("Withdrawal order created: {}", withdrawalOrder.getOrderNo());

                return withdrawalOrder.getOrderNo();
            });
        });
    }

    @Override
    public WithdrawalOrder cancelWithdrawal(String anUserId, String withdrawalOrderNo) {

        Validate.notBlank(anUserId, "User ID must not be blank");
        Validate.notBlank(withdrawalOrderNo, "Withdrawal order number must not be blank");

        UserId userId = new UserId(anUserId);
        WithdrawalOrderNo orderNo = new WithdrawalOrderNo(withdrawalOrderNo);

        WithdrawalOrder order = withdrawalOrderRepository.userWithdrawalOrder(userId, orderNo);
        if (order == null) {
            throw new IllegalArgumentException("Withdrawal order not found");
        }

        // 获取钱包锁
        Lock walletLock = walletOperationService.getWalletLock(order.getWalletId());
        return LockTemplate.execute(walletLock, () -> {
            // 开启事务，取消出金订单
            return transactionTemplate.execute(txStatus -> {
                order.cancel(WithdrawalOrderStatus.CANCELLED, walletOperationService);
                log.info("Withdrawal order cancelled: {}", order.getOrderNo());
                return withdrawalOrderRepository.save(order);
            });
        });
    }
}
