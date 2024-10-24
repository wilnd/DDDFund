package com.finpoints.bss.fund.port.adapter.service;

import com.finpoints.bss.common.lock.LockProvider;
import com.finpoints.bss.common.lock.WLock;
import com.finpoints.bss.fund.domain.model.wallet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.WeakHashMap;

@Service
public class WalletOperationServiceImpl implements WalletOperationService {

    private static final Logger log = LoggerFactory.getLogger(WalletOperationServiceImpl.class);
    private final LockProvider lockProvider;
    private final WeakHashMap<WalletId, WLock> walletLockMap = new WeakHashMap<>();

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final FrozenTransactionRepository frozenTransactionRepository;

    public WalletOperationServiceImpl(LockProvider lockProvider,
                                      WalletRepository walletRepository,
                                      WalletTransactionRepository walletTransactionRepository,
                                      FrozenTransactionRepository frozenTransactionRepository) {
        this.lockProvider = lockProvider;
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.frozenTransactionRepository = frozenTransactionRepository;
    }

    @Override
    public WLock getWalletLock(WalletId walletId) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null) {
            lock = lockProvider.getLock(WALLET_LOCK_KEY_PREFIX + walletId.rawId());
            walletLockMap.put(walletId, lock);
        }
        return lock;
    }

    @Override
    public FrozenTransactionId freezeBalance(WalletId walletId, FrozenType freezeType, BigDecimal amount,
                                             String idemKey, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

        FrozenTransaction transaction = frozenTransactionRepository.findByIdemKey(idemKey);
        if (transaction != null) {
            log.warn("Frozen transaction is already exist, idemKey: {}", idemKey);
            return transaction.getTransactionId();
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 冻结资金
        transaction = wallet.freeze(freezeType, amount, idemKey, remark);
        frozenTransactionRepository.save(transaction);
        log.info("Freeze balance, walletId: {}, type: {}, amount: {}, frozenAmount: {}",
                walletId, freezeType, amount, transaction.getAmount());

        walletRepository.save(wallet);
        return transaction.getTransactionId();
    }

    @Override
    public void unfreezeBalance(WalletId walletId, FrozenTransactionId transactionId, FrozenType unfreezeType, BigDecimal amount,
                                String serviceCurrency, BigDecimal serviceCharge, String idemKey, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

        FrozenTransaction transaction = frozenTransactionRepository.findById(transactionId);
        if (transaction == null) {
            log.error("Frozen transaction is not found, transactionId: {}", transactionId);
            throw new RuntimeException("Frozen transaction is not found");
        }
        if (transaction.getAmount().compareTo(amount) != 0) {
            log.error("Unfreeze amount is not equal to frozen amount, transactionId: {}, amount: {}, frozenAmount: {}",
                    transactionId, amount, transaction.getAmount());
            throw new RuntimeException("Unfreeze amount is not equal to frozen amount");
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 解冻资金
        transaction = wallet.unfreeze(unfreezeType, transaction, remark);
        frozenTransactionRepository.save(transaction);
        log.info("Unfreeze balance, walletId: {}, type: {}, amount: {}, unfrozenAmount: {}",
                walletId, unfreezeType, amount, transaction.getAmount());

        // 扣除手续费
        if (serviceCharge != null && serviceCharge.compareTo(BigDecimal.ZERO) > 0) {
            WalletTransaction serviceChargeTransaction = wallet.deduct(serviceCharge);
            log.info("Deduct service charge, walletId: {}, serviceCharge: {}, serviceCurrency: {}",
                    walletId, serviceCharge, serviceCurrency);
            walletTransactionRepository.save(serviceChargeTransaction);
        }

        walletRepository.save(wallet);
    }

    @Override
    public void increaseFrozenAmount(WalletId walletId, BigDecimal amount, String idemKey) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

    }

    @Override
    public void deductFrozenAmount(WalletId walletId, BigDecimal amount, String idemKey) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

    }
}
