package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.lock.WLock;

import java.math.BigDecimal;

public interface WalletOperationService {

    String WALLET_LOCK_KEY_PREFIX = "lock:fund:wallet:";

    /**
     * 获取钱包锁
     * 钱包操作需要加锁，以保证并发操作的正确性
     *
     * @param walletId 钱包ID
     * @return 钱包锁
     */
    WLock getWalletLock(WalletId walletId);

    /**
     * 冻结钱包资金
     *
     * @param walletId 钱包ID
     * @param amount   冻结金额
     * @param idemKey  幂等key，由业务决定
     */
    FrozenTransactionId freezeWalletAmount(WalletId walletId, FrozenType freezeType,
                                           BigDecimal amount, String idemKey, String remark);


    /**
     * 解冻钱包资金
     *
     * @param walletId     钱包ID
     * @param unfreezeType 解冻类型
     * @param amount       解冻金额
     * @param idemKey      幂等key，由业务决定
     */
    default void unfreezeWalletAmount(WalletId walletId, FrozenTransactionId transactionId, FrozenType unfreezeType,
                                      BigDecimal amount, String idemKey, String remark) {
        unfreezeWalletAmount(walletId, transactionId, unfreezeType, amount, null, BigDecimal.ZERO, idemKey, remark);
    }

    /**
     * 解冻钱包资金
     *
     * @param walletId        钱包ID
     * @param unfreezeType    解冻类型
     * @param amount          解冻金额
     * @param serviceCharge   服务费
     * @param serviceCurrency 服务费币种
     * @param idemKey         幂等key，由业务决定
     */
    void unfreezeWalletAmount(WalletId walletId, FrozenTransactionId transactionId, FrozenType unfreezeType, BigDecimal amount,
                              String serviceCurrency, BigDecimal serviceCharge, String idemKey, String remark);

    /**
     * 增加钱包冻结资金
     *
     * @param walletId 钱包ID
     * @param amount   增加金额
     * @param idemKey  幂等key，由业务决定
     */
    void addWalletFreezeAmount(WalletId walletId, BigDecimal amount, String idemKey);

    /**
     * 扣减冻结冻结资金
     *
     * @param walletId 钱包ID
     * @param amount   扣减金额
     * @param idemKey  幂等key，由业务决定
     */
    void deductWalletFreezeAmount(WalletId walletId, BigDecimal amount, String idemKey);
}
