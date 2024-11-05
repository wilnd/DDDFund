package com.finpoints.bss.fund.jpa.withdrawal;

import com.finpoints.bss.fund.domain.model.common.BankInfo;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderStatus;
import com.finpoints.bss.fund.jpa.JpaVersionalEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Table(name = "withdrawal_order")
@AllArgsConstructor
public class JpaWithdrawalOrder extends JpaVersionalEntityBase {

    @Comment("出金订单号")
    @Column(length = 64, unique = true)
    private String orderNo;

    @Comment("用户ID")
    @Column(length = 64)
    private String userId;

    @Comment("出金钱包ID")
    @Column(length = 64)
    private String walletId;

    @Comment("出金钱包类型")
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @Comment("出金方式")
    @Enumerated(EnumType.STRING)
    private WithdrawalMethod withdrawalMethod;

    @Comment("请求时间")
    private Instant requestTime;

    @Comment("汇率")
    @Column(precision = 20, scale = 10)
    private BigDecimal exchangeRate;

    @Embedded
    @Comment("原始出金币种")
    @AttributeOverride(name = "code", column = @Column(name = "originalCurrency", length = 8))
    private Currency originalCurrency;

    @Embedded
    @Comment("目标出金币种")
    @AttributeOverride(name = "code", column = @Column(name = "targetCurrency", length = 8))
    private Currency targetCurrency;

    @Comment("出金金额")
    @Column(precision = 20, scale = 10)
    private BigDecimal amount;

    @Comment("到账金额")
    @Column(precision = 20, scale = 10)
    private BigDecimal arrivalAmount;

    @Comment("服务费")
    @Column(precision = 20, scale = 10)
    private BigDecimal serviceCharge;

    @Comment("(银行出金)银行ID")
    @Column(length = 64)
    private String bankId;

    @Comment("(银行出金)银行账号")
    @Column(length = 32)
    private String bankAccount;

    // (银行出金)银行信息
    @Embedded
    private BankInfo bankInfo;

    @Comment("(国际电汇)中转银行ID")
    @Column(length = 64)
    private String intermediaryBankId;

    @Comment("(国际电汇)中转银行账号")
    @Column(length = 32)
    private String intermediaryBankAccount;

    // (国际电汇)中转银行信息
    @Embedded
    private BankInfo intermediaryBankInfo;

    @Comment("订单状态")
    @Enumerated(EnumType.STRING)
    private WithdrawalOrderStatus status;
    
    @Comment("MT出金请求ID，仅在出金订单为MT出金时有值")
    @Column(length = 64)
    private String mtRequestId;

    @Comment("冻结流水ID")
    @Column(length = 64)
    private String frozenFlowId;

    @Comment("回撤时间")
    private Instant recallTime;

    protected JpaWithdrawalOrder() {
    }

}
