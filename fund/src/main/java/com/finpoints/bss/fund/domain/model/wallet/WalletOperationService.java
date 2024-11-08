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
     * @param orderNo  业务订单号
     */
    FrozenFlowId freezeWalletAmount(WalletId walletId, FrozenType freezeType,
                                    BigDecimal amount, String orderNo, String remark);


    /**
     * 解冻钱包资金
     *
     * @param walletId     钱包ID
     * @param unfreezeType 解冻类型
     * @param amount       解冻金额
     */
    default void unfreezeWalletAmount(WalletId walletId, FrozenFlowId flowId, FrozenType unfreezeType,
                                      BigDecimal amount, String remark) {
        unfreezeWalletAmount(walletId, flowId, unfreezeType, amount, BigDecimal.ZERO, remark);
    }

    /**
     * 解冻钱包资金
     *
     * @param walletId      钱包ID
     * @param unfreezeType  解冻类型
     * @param amount        解冻金额
     * @param serviceCharge 服务费
     */
    void unfreezeWalletAmount(WalletId walletId, FrozenFlowId transactionId, FrozenType unfreezeType,
                              BigDecimal amount, BigDecimal serviceCharge, String remark);

    /**
     * 扣除冻结资金
     *
     * @param walletId 钱包ID
     * @param flowId   冻结流水ID
     * @param amount   扣除金额
     * @param remark   备注
     * @return 流水ID
     */
    WalletFlowId deductFrozenAmount(WalletId walletId, FrozenFlowId flowId, BigDecimal amount, String remark);

    /**
     * 增加冻结资金
     *
     * @param walletId 钱包ID
     * @param amount   增加金额
     * @param remark   备注
     * @return 流水ID
     */
    WalletFlowId increaseFrozenAmount(WalletId walletId, FrozenType freezeType, BigDecimal amount,
                                      String orderNo, String remark);

}
