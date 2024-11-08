package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserRole;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowType;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "wallet_flow")
@AllArgsConstructor
public class JpaWalletFlow extends JpaEntityBase {

    @Comment("流水ID")
    @Column(length = 64, unique = true)
    private String flowId;

    @Comment("钱包类型")
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @Comment("钱包ID")
    @Column(length = 64)
    private String walletId;

    @Comment("用户角色")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Comment("用户ID")
    @Column(length = 64)
    private String userId;

    @Comment("流水类型")
    @Enumerated(EnumType.STRING)
    private WalletFlowType flowType;

    @Comment("业务订单号")
    @Column(length = 64)
    private String businessOrderNo;

    @Comment("币种")
    @AttributeOverride(name = "code", column = @Column(name = "currency", length = 3))
    private Currency currency;

    @Comment("原始余额")
    @Column(precision = 20, scale = 2)
    private BigDecimal originalBalance;

    @Comment("变动金额")
    @Column(precision = 20, scale = 2)
    private BigDecimal balanceChange;

    @Comment("现有余额")
    @Column(precision = 20, scale = 2)
    private BigDecimal existingBalance;

    @Comment("备注")
    private String remark;

    @Embedded
    private Operator operator;

    protected JpaWalletFlow() {
    }
}
