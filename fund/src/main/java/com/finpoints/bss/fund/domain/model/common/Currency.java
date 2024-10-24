package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

import java.util.Objects;

/**
 * 货币
 */
@Getter
public class Currency extends ValueObject {

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
