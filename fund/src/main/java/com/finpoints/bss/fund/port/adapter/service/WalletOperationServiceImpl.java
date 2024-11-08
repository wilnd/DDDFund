package com.finpoints.bss.fund.port.adapter.service;

import com.finpoints.bss.common.lock.LockProvider;
import com.finpoints.bss.common.lock.WLock;
import com.finpoints.bss.fund.domain.model.wallet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
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
                                           String orderNo, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not held by current thread");
        }

        FrozenFlow flow = frozenFlowRepository.orderFlow(walletId, freezeType, orderNo);
        if (flow != null) {
            log.warn("Frozen flow {} is already exist, orderNo: {}", freezeType, orderNo);
            return flow.getFlowId();
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 冻结资金
        flow = wallet.freeze(frozenFlowRepository.nextId(), freezeType, amount, orderNo, remark);
        frozenFlowRepository.save(flow);
        log.info("Freeze balance, walletId: {}, type: {}, amount: {}, frozenAmount: {}",
                walletId, freezeType, amount, flow.getAmount());

        walletRepository.save(wallet);
        return flow.getFlowId();
    }

    @Override
    public void unfreezeWalletAmount(WalletId walletId, FrozenFlowId flowId, FrozenType unfreezeType,
                                     BigDecimal amount, BigDecimal serviceCharge, String remark) {
        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not held by current thread");
        }

        FrozenFlow flow = frozenFlowRepository.findById(flowId);
        if (flow == null) {
            log.error("Frozen flow is not found, flowId: {}", flowId);
            throw new RuntimeException("Frozen flow is not found");
        }
        if (flow.getAmount().compareTo(amount) != 0) {
            log.error("Unfreeze amount is not equal to frozen amount, flowId: {}, amount: {}, frozenAmount: {}",
                    flowId, amount, flow.getAmount());
            throw new RuntimeException("Unfreeze amount is not equal to frozen amount");
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 解冻资金
        flow = wallet.unfreeze(unfreezeType, flow, remark);
        frozenFlowRepository.save(flow);

        log.info("Unfreeze balance, walletId: {}, type: {}, amount: {}, unfrozenAmount: {}",
                walletId, unfreezeType, amount, flow.getAmount());

        // 扣除手续费
        if (serviceCharge != null && serviceCharge.compareTo(BigDecimal.ZERO) > 0) {
            WalletFlowId walletFlowId = walletFlowRepository.nextId();
            WalletFlow serviceChargeFlow = wallet.deduct(walletFlowId,
                    WalletFlowType.from(unfreezeType),
                    serviceCharge,
                    flow.getBusinessOrderNo(),
                    remark
            );
            log.info("Deduct service charge, walletId: {}, serviceCharge: {}",
                    walletId, serviceCharge);
            walletFlowRepository.save(serviceChargeFlow);
        }

        walletRepository.save(wallet);
    }

    @Override
    public WalletFlowId deductFrozenAmount(WalletId walletId, FrozenFlowId flowId, BigDecimal amount, String remark) {

        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not held by current thread");
        }

        FrozenFlow frozenFlow = frozenFlowRepository.findById(flowId);
        if (frozenFlow == null) {
            log.error("Frozen flow is not found, flowId: {}", flowId);
            throw new RuntimeException("Frozen flow is not found");
        }
        if (frozenFlow.getAmount().compareTo(amount) != 0) {
            log.error("Deduct amount is not equal to frozen amount, flowId: {}, amount: {}, frozenAmount: {}",
                    flowId, amount, frozenFlow.getAmount());
            throw new RuntimeException("Deduct amount is not equal to frozen amount");
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 扣除冻结资金
        WalletFlow walletFlow = wallet.deductFrozen(walletFlowRepository.nextId(), frozenFlow, remark);
        walletFlowRepository.save(walletFlow);
        // 完成冻结流水
        frozenFlow.completed();
        frozenFlowRepository.save(frozenFlow);

        log.info("Deduct frozen balance, walletId: {}, amount: {}, frozenAmount: {}",
                walletId, amount, frozenFlow.getAmount());

        walletRepository.save(wallet);
        return walletFlow.getFlowId();
    }

    @Override
    public WalletFlowId increaseFrozenAmount(WalletId walletId, FrozenType freezeType, BigDecimal amount,
                                             String orderNo, String remark) {
        Validate.notNull(orderNo, "OrderNo is required");

        WLock lock = walletLockMap.get(walletId);
        if (lock == null || !lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Wallet lock is not held by current thread");
        }
        FrozenFlow frozenFlow = frozenFlowRepository.orderFlow(walletId, freezeType, orderNo);
        if (frozenFlow != null) {
            log.warn("Frozen flow {} is already exist, orderNo: {}", freezeType, orderNo);
            return null;
        }

        Wallet wallet = walletRepository.findById(walletId);
        if (wallet == null) {
            throw new RuntimeException("Wallet is not found");
        }

        // 创建冻结流水
        frozenFlow = new FrozenFlow(
                wallet.getAppId(),
                frozenFlowRepository.nextId(),
                wallet.getWalletId(),
                wallet.getUserId(),
                orderNo,
                wallet.getCurrency(),
                freezeType,
                amount,
                remark
        );
        // 增加冻结资金
        WalletFlow walletFlow = wallet.increaseFrozen(walletFlowRepository.nextId(), frozenFlow, remark);
        walletFlowRepository.save(walletFlow);
        frozenFlowRepository.save(frozenFlow);
        log.info("Increase frozen balance, walletId: {}, type: {}, amount: {}, frozenAmount: {}",
                walletId, freezeType, amount, frozenFlow.getAmount());

        walletRepository.save(wallet);
        return walletFlow.getFlowId();
    }
}
