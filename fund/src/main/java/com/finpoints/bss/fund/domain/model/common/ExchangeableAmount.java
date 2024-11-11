package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class ExchangeableAmount extends ValueObject {

    private final Currency originalCcy;
    private final Currency targetCcy;
    private final BigDecimal rate;

    private final BigDecimal amount;

    public ExchangeableAmount(Currency originalCcy, Currency targetCcy, BigDecimal rate, BigDecimal amount) {
        this.originalCcy = originalCcy;
        this.targetCcy = targetCcy;
        this.rate = rate;
        this.amount = amount;
    }

    /**
     * Exchange the amount to target currency
     *
     * @return the exchanged amount
     */
    public BigDecimal exchange() {
        // // 小数位处理
        //    fixedNumber(value: string | number, num: number = 2) {
        //        return Number(Number(value).toFixed(num));
        //    },
        return amount.multiply(rate).setScale(targetCcy.precision(), RoundingMode.HALF_UP);
    }

    public static ExchangeableAmount usd(BigDecimal amount) {
        return new ExchangeableAmount(Currency.USD, Currency.USD, BigDecimal.ONE, amount);
    }

    public static ExchangeableAmount usd(Currency original, BigDecimal rate, BigDecimal amount) {
        return new ExchangeableAmount(original, Currency.USD, rate, amount);
    }

    public static ExchangeableAmount usdZero() {
        return usd(BigDecimal.ZERO);
    }
}
