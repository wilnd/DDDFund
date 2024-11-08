package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FrozenFlow extends Entity {

    /**
     * 冻结流水ID
     */
    private final FrozenFlowId flowId;

    /**
     * 钱包ID
     */
    private final WalletId walletId;

    /**
     * 用户ID
     */
    private final UserId userId;

    /**
     * 关联业务订单号
     */
    private final String businessOrderNo;

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

    public FrozenFlow(String appId, FrozenFlowId flowId, WalletId walletId, UserId userId, String businessOrderNo,
                      Currency currency, FrozenType freezeType, BigDecimal amount, String remark) {
        super(appId);

        Validate.notNull(flowId, "flowId is null");
        Validate.notNull(walletId, "walletId is null");
        Validate.notNull(userId, "userId is null");
        Validate.notNull(currency, "currency is null");
        Validate.notNull(freezeType, "freezeType is null");
        Validate.notNull(amount, "amount is null");
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "amount must be greater than 0");

        this.flowId = flowId;
        this.businessOrderNo = businessOrderNo;
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

    public void completed() {
        this.status = FrozenStatus.COMPLETED;
    }
}
