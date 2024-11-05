package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.*;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettingsService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class WithdrawalToBankStrategy extends MtWithdrawalStrategyDelegate {

    private final BankInfoService bankInfoService;
    private final ExchangeRateService exchangeRateService;
    private final WithdrawalSettingsService withdrawalSettingsService;

    public WithdrawalToBankStrategy(MtRequestRepository mtRequestRepository,
                                    BankInfoService bankInfoService,
                                    ExchangeRateService exchangeRateService,
                                    WithdrawalSettingsService withdrawalSettingsService) {
        super(mtRequestRepository);
        this.bankInfoService = bankInfoService;
        this.exchangeRateService = exchangeRateService;
        this.withdrawalSettingsService = withdrawalSettingsService;
    }

    @Override
    protected WithdrawalOrder doWithdrawal(WithdrawalOrderNo orderNo, UserId userId,
                                           WalletId walletId, WalletType walletType,
                                           Instant requestTime, String remark,
                                           BigDecimal amount, Currency currency,
                                           BankId bankId, BankCardId bankCardId,
                                           MtRequestId mtRequestId) {
        BankCardInfo cardInfo = bankInfoService.bankCardInfo(bankCardId);
        if (cardInfo == null) {
            throw new IllegalArgumentException("Bank card not found: " + bankCardId);
        }
        Currency targetCurrency = cardInfo.getCurrency();
        ExchangeRate exchangeRate = exchangeRateService.exchangeRate(currency, targetCurrency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Exchange rate not found: " + currency + " -> " + targetCurrency);
        }
        BigDecimal targetAmount = exchangeRate.calculateSellAmount(amount);

        return WithdrawalOrder.ofBank(
                orderNo,
                userId,
                walletId,
                walletType,
                requestTime,
                exchangeRate.getSellRate(),
                currency,
                targetCurrency,
                amount,
                targetAmount,
                bankId,
                null,
                null,
                mtRequestId
        );
    }
}
