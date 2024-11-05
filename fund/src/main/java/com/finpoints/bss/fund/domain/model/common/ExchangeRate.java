package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 汇率
 */
@Getter
public class ExchangeRate extends ValueObject {

    private final Currency from;
    private final Currency to;

    private final LocalDateTime rateTime;

    private final BigDecimal buyRate;
    private final BigDecimal middleRate;
    private final BigDecimal sellRate;

    public ExchangeRate(Currency from, Currency to, LocalDateTime rateTime,
                        BigDecimal buyRate, BigDecimal middleRate, BigDecimal sellRate) {
        this.from = from;
        this.to = to;
        this.rateTime = rateTime;
        this.buyRate = buyRate;
        this.middleRate = middleRate;
        this.sellRate = sellRate;
    }

    public BigDecimal calculateBuyAmount(BigDecimal amount) {
        return amount.multiply(buyRate);
    }

    public BigDecimal calculateSellAmount(BigDecimal amount) {
        return amount.multiply(sellRate);
    }

    public BigDecimal calculateMiddleAmount(BigDecimal amount) {
        return amount.multiply(middleRate);
    }
}
