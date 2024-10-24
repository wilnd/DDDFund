package com.finpoints.bss.fund.port.adapter.mock;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettings;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettingsService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MockWithdrawalSettingServiceImpl implements WithdrawalSettingsService {

    @Override
    public WithdrawalSettings getUserSetting(UserId userId) {
        return new WithdrawalSettings(
                true,
                Currency.USD,
                new BigDecimal("100")
        );
    }
}
