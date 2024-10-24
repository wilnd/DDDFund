package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.ValueObject;
import com.finpoints.bss.fund.domain.model.common.Currency;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 出金设置
 */
@Getter
public class WithdrawalSettings extends ValueObject {

    private final Boolean autoApproval;

    private final Currency commissionCurrency;
    private final BigDecimal commission;

    public WithdrawalSettings(boolean autoApproval, Currency commissionCurrency, BigDecimal commission) {
        this.autoApproval = autoApproval;
        this.commissionCurrency = commissionCurrency;
        this.commission = commission;
    }
}
