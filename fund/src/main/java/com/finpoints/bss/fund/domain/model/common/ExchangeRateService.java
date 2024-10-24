package com.finpoints.bss.fund.domain.model.common;

public interface ExchangeRateService {

    /**
     * 获取汇率
     *
     * @param fromCurrency 源币种
     * @param toCurrency   目标币种
     * @return 汇率
     */
    ExchangeRate exchangeRate(Currency fromCurrency, Currency toCurrency);
}
