package com.finpoints.bss.fund.jpa.common;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.ExchangeableAmount;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class JpaExchangeableAmount {

    private String originalCcy;
    private String targetCcy;
    private BigDecimal rate;

    private BigDecimal amount;

    public JpaExchangeableAmount() {
    }

    public JpaExchangeableAmount(String originalCcy, String targetCcy, BigDecimal rate, BigDecimal amount) {
        this.originalCcy = originalCcy;
        this.targetCcy = targetCcy;
        this.rate = rate;
        this.amount = amount;
    }

    public static JpaExchangeableAmount from(ExchangeableAmount amount) {
        return new JpaExchangeableAmount(
                amount.getOriginalCcy().getCode(),
                amount.getTargetCcy().getCode(),
                amount.getRate(),
                amount.getAmount()
        );
    }

    public ExchangeableAmount toExchangeableAmount() {
        return new ExchangeableAmount(
                new Currency(originalCcy),
                new Currency(targetCcy),
                rate,
                amount
        );
    }

}
