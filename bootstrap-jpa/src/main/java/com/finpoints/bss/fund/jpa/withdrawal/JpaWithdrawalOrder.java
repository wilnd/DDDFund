package com.finpoints.bss.fund.jpa.withdrawal;

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

    @Comment("订单状态")
    @Enumerated(EnumType.STRING)
    private WithdrawalOrderStatus status;

    /**
     * 仅在出金订单为MT出金时有值
     */
    @Comment("MT出金请求ID")
    @Column(length = 64)
    private String mtRequestId;

    @Comment("冻结流水ID")
    @Column(length = 64)
    private String frozenFlowId;

    protected JpaWithdrawalOrder() {
    }

}
