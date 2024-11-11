package com.finpoints.bss.fund.domain.model.common;

import java.math.BigDecimal;

public class USDAmount extends ExchangeableAmount {

    public USDAmount(Currency original, BigDecimal rate, BigDecimal amount) {
        super(original, Currency.USD, rate, amount);
    }
}
