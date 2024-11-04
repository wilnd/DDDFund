package com.finpoints.bss.fund.port.adapter.service;

import com.finpoints.bss.common.lock.LockProvider;
import com.finpoints.bss.common.lock.WLock;
import com.finpoints.bss.fund.domain.model.wallet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.WeakHashMap;

@Slf4j
@Service
public class WalletOperationServiceImpl implements WalletOperationService {

    private final LockProvider lockProvider;
    private final WeakHashMap<WalletId, WLock> walletLockMap = new WeakHashMap<>();

    private final WalletRepository walletRepository;
    private final WalletFlowRepository walletFlowRepository;
    private final FrozenFlowRepository frozenFlowRepository;

    public WalletOperationServiceImpl(LockProvider lockProvider,
                                      WalletRepository walletRepository,
                                      WalletFlowRepository walletFlowRepository,
                                      FrozenFlowRepository frozenFlowRepository) {
        this.lockProvider = lockProvider;
        this.walletRepository = walletRepository;
        this.walletFlowRepository = walletFlowRepository;
        this.frozenFlowRepository = frozenFlowRepository;
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
    public FrozenFlowId freezeWalletAmount(WalletId walletId, FrozenType freezeType, BigDecimal amount,
                                           String idemKey, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

        FrozenFlow transaction = frozenFlowRepository.findByIdemKey(idemKey);
        if (transaction != null) {
            log.warn("Frozen transaction is already exist, idemKey: {}", idemKey);
            return transaction.getFlowId();
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 冻结资金
        transaction = wallet.freeze(frozenFlowRepository.nextId(), freezeType, amount, idemKey, remark);
        frozenFlowRepository.save(transaction);
        log.info("Freeze balance, walletId: {}, type: {}, amount: {}, frozenAmount: {}",
                walletId, freezeType, amount, transaction.getAmount());

        walletRepository.save(wallet);
        return transaction.getFlowId();
    }

    @Override
    public void unfreezeWalletAmount(WalletId walletId, FrozenFlowId transactionId, FrozenType unfreezeType, BigDecimal amount,
                                     String serviceCurrency, BigDecimal serviceCharge, String idemKey, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

        FrozenFlow transaction = frozenFlowRepository.findById(transactionId);
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
        frozenFlowRepository.save(transaction);
        log.info("Unfreeze balance, walletId: {}, type: {}, amount: {}, unfrozenAmount: {}",
                walletId, unfreezeType, amount, transaction.getAmount());

        // 扣除手续费
        if (serviceCharge != null && serviceCharge.compareTo(BigDecimal.ZERO) > 0) {
            WalletFlow serviceChargeTransaction = wallet.deduct(serviceCharge);
            log.info("Deduct service charge, walletId: {}, serviceCharge: {}, serviceCurrency: {}",
                    walletId, serviceCharge, serviceCurrency);
            walletFlowRepository.save(serviceChargeTransaction);
        }

        walletRepository.save(wallet);
    }

    @Override
    public void addWalletFreezeAmount(WalletId walletId, BigDecimal amount, String idemKey) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

    }

    @Override
    public void deductWalletFreezeAmount(WalletId walletId, BigDecimal amount, String idemKey) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not found or not locked");
        }

    }
}
