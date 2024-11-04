package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.wallet.FrozenStatus;
import com.finpoints.bss.fund.domain.model.wallet.FrozenType;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "frozen_transaction")
@AllArgsConstructor
public class JpaFrozenFlow extends JpaEntityBase {

    @Comment("冻结流水ID")
    @Column(length = 64, unique = true)
    private String flowId;

    @Comment("幂等Key")
    @Column(length = 128, unique = true)
    private String idemKey;

    @Comment("钱包ID")
    @Column(length = 64)
    private String walletId;

    @Comment("用户ID")
    private String userId;

    @Embedded
    @Comment("币种")
    @AttributeOverride(name = "code", column = @Column(name = "currency", length = 3))
    private Currency currency;

    @Comment("冻结类型")
    @Enumerated(EnumType.STRING)
    private FrozenType freezeType;

    @Comment("冻结金额")
    @Column(precision = 20, scale = 6)
    private BigDecimal amount;

    @Comment("冻结状态")
    @Enumerated(EnumType.STRING)
    private FrozenStatus status;

    @Comment("备注")
    @Column(length = 512)
    private String remark;

    @Comment("解冻类型")
    @Enumerated(EnumType.STRING)
    private FrozenType unfreezeType;

    protected JpaFrozenFlow() {

    }
}
