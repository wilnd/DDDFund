package com.finpoints.bss.fund.port.adapter.mock;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.ExchangeRate;
import com.finpoints.bss.fund.domain.model.common.ExchangeRateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MockExchangeRateServiceImpl implements ExchangeRateService {

    @Override
    public ExchangeRate exchangeRate(Currency fromCurrency, Currency toCurrency) {
        return new ExchangeRate(
                fromCurrency,
                toCurrency,
                LocalDateTime.now(),
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE
        );
    }
}
