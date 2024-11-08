package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WalletFlow extends Entity {

    /**
     * 流水ID
     */
    private final WalletFlowId flowId;

    /**
     * 钱包类型
     */
    private final WalletType walletType;

    /**
     * 钱包ID
     */
    private final WalletId walletId;

    /**
     * 用户角色
     */
    private final UserRole userRole;

    /**
     * 用户ID
     */
    private final UserId userId;

    /**
     * 流水类型
     */
    private final WalletFlowType flowType;

    /**
     * 关联业务订单号
     */
    private final String businessOrderNo;

    /**
     * 货币币种
     */
    private final Currency currency;

    /**
     * 原始余额
     */
    private final BigDecimal originalBalance;

    /**
     * 变动金额
     */
    private final BigDecimal balanceChange;

    /**
     * 现有余额
     */
    private final BigDecimal existingBalance;

    /**
     * 备注
     */
    private final String remark;

    /**
     * 操作员
     */
    private final Operator operator;

    public WalletFlow(String appId, WalletFlowId flowId,
                      WalletType walletType, WalletId walletId,
                      UserRole userRole, UserId userId,
                      WalletFlowType flowType, String businessOrderNo,
                      Currency currency, BigDecimal originalBalance,
                      BigDecimal balanceChange, BigDecimal existingBalance,
                      String remark, Operator operator) {
        super(appId);

        this.flowId = flowId;
        this.walletType = walletType;
        this.walletId = walletId;
        this.userRole = userRole;
        this.userId = userId;
        this.flowType = flowType;
        this.businessOrderNo = businessOrderNo;
        this.currency = currency;
        this.originalBalance = originalBalance;
        this.balanceChange = balanceChange;
        this.existingBalance = existingBalance;
        this.remark = remark;
        this.operator = operator;
    }
}
