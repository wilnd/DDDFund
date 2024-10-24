package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.*;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawalToBankStrategy implements WithdrawalMethodStrategy {

    private final BankInfoService bankInfoService;
    private final ExchangeRateService exchangeRateService;
    private final WithdrawalSettingsService withdrawalSettingsService;

    public WithdrawalToBankStrategy(BankInfoService bankInfoService,
                                    ExchangeRateService exchangeRateService,
                                    WithdrawalSettingsService withdrawalSettingsService) {
        this.bankInfoService = bankInfoService;
        this.exchangeRateService = exchangeRateService;
        this.withdrawalSettingsService = withdrawalSettingsService;
    }

    @Override
    public WithdrawalOrder withdrawal(UserId userId, WalletId walletId, WalletType walletType,
                                      WithdrawalMethod withdrawalMethod, BigDecimal amount,
                                      Currency originalCurrency, String remark, BankId bankId, BankCardId bankCardId,
                                      WithdrawalOrderNo orderNo, String mtAccount, String serverId) {

        BankCardInfo cardInfo = bankInfoService.bankCardInfo(bankCardId);
        if (cardInfo == null) {
            throw new IllegalArgumentException("Bank card not found: " + bankCardId);
        }
        Currency targetCurrency = cardInfo.getCurrency();
        ExchangeRate exchangeRate = exchangeRateService.exchangeRate(originalCurrency, targetCurrency);

        return new WithdrawalOrder(
                orderNo,
                userId,
                walletId,
                walletType,
                withdrawalMethod,
                exchangeRate.getSellRate(),
                originalCurrency,
                targetCurrency,
                amount,
                null
        );
    }
}
