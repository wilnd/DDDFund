package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

/**
 * 货币
 */
@Getter
public class Currency extends ValueObject {

    private static final Set<String> CRYPTO_CURRENCIES = Set.of("BTC", "ETH", "USDT");

    public static final Currency CNY = new Currency("CNY");
    public static final Currency USD = new Currency("USD");
    public static final Currency HKD = new Currency("HKD");

    private final String code;

    public Currency(String code) {
        this.code = code;
    }

    protected Currency() {
        this.code = null;
    }

    public int precision() {
        // 获取币种对应小数位
        // getDps(currency: string) {
        //   if (['USD', 'JPY', 'CNY', 'CAD', 'HKD', 'CHF', 'EUR', 'GBP', 'KRW'].includes(currency)) return 2;
        //   if (['BTC', 'ETH', 'USDT'].includes(currency)) return 8;
        //   return 2;
        // },
        if (CRYPTO_CURRENCIES.contains(code)) {
            return 8;
        }
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                '}';
    }
}
