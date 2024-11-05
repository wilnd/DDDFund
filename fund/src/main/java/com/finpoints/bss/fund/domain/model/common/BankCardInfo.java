package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class BankCardInfo extends ValueObject {

    private final Currency currency;

    public BankCardInfo(Currency currency) {
        this.currency = currency;
    }
}
