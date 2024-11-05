package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "wallet")
@AllArgsConstructor
public class JpaWallet extends JpaEntityBase {

    @Comment("钱包ID")
    @Column(length = 64, unique = true)
    private String walletId;

    @Comment("用户ID")
    @Column(length = 64)
    private String userId;

    @Embedded
    @Comment("币种")
    @AttributeOverride(name = "code", column = @Column(name = "currency", length = 3))
    private Currency currency;

    @Comment("钱包类型")
    @Enumerated(EnumType.STRING)
    private WalletType type;

    @Column(columnDefinition = "boolean default false comment '是否为主钱包，默认否'")
    private Boolean mainWallet;

    @Column(columnDefinition = "decimal(20,8) default 0 comment '账户余额'")
    private BigDecimal balance;

    @Column(columnDefinition = "decimal(20,8) default 0 comment '冻结余额'")
    private BigDecimal frozenBalance;

    @Column(columnDefinition = "decimal(20,8) default 0 comment '可用余额'")
    private BigDecimal availableBalance;

    @Column(columnDefinition = "decimal(20,8) default 0 comment '可提余额'")
    private BigDecimal drawableBalance;

    protected JpaWallet() {
    }
}
