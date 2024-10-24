package com.finpoints.bss.fund.port.adapter.mock;

import com.finpoints.bss.fund.domain.model.common.*;
import org.springframework.stereotype.Component;

@Component
public class MockBankInfoServiceImpl implements BankInfoService {

    @Override
    public BankInfo bankInfo(BankId bankId) {
        return null;
    }

    @Override
    public BankCardInfo bankCardInfo(BankCardId bankCardId) {
        return new BankCardInfo(
                bankCardId,
                Currency.USD
        );
    }
}
