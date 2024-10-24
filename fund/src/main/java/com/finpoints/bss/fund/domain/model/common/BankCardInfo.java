package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class BankCardInfo extends ValueObject {

    private final BankCardId bankCardId;
    private final Currency currency;

    public BankCardInfo(BankCardId bankCardId, Currency currency) {
        this.bankCardId = bankCardId;
        this.currency = currency;
    }
}
