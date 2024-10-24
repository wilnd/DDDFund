package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FrozenTransaction extends Entity {

    /**
     * 冻结流水ID
     */
    private final FrozenTransactionId transactionId;

    /**
     * 幂等Key
     */
    private final String idemKey;

    /**
     * 钱包ID
     */
    private final WalletId walletId;

    /**
     * 用户ID
     */
    private final UserId userId;

    /**
     * 币种
     */
    private final Currency currency;

    /**
     * 冻结类型
     */
    private final FrozenType freezeType;

    /**
     * 冻结金额
     */
    private final BigDecimal amount;

    /**
     * 冻结状态
     */
    private FrozenStatus status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 解冻类型
     */
    private FrozenType unfreezeType;

    public FrozenTransaction(FrozenTransactionId transactionId, String idemKey, WalletId walletId, UserId userId, Currency currency,
                             FrozenType freezeType, BigDecimal amount, String remark) {
        this.transactionId = transactionId;
        this.idemKey = idemKey;
        this.walletId = walletId;
        this.userId = userId;
        this.currency = currency;
        this.freezeType = freezeType;
        this.amount = amount;
        this.remark = remark;
    }

    public void unfreeze(FrozenType unfreezeType, String remark) {
        this.status = FrozenStatus.UNFROZEN;
        this.unfreezeType = unfreezeType;
        this.remark = remark;
    }
}
