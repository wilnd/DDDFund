package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class WithdrawalToWireStrategy extends MtWithdrawalStrategyDelegate {

    public WithdrawalToWireStrategy(MtRequestRepository mtRequestRepository) {
        super(mtRequestRepository);
    }

    @Override
    protected WithdrawalOrder doWithdrawal(String appId, WithdrawalOrderNo orderNo, UserId userId,
                                           WalletId walletId, WalletType walletType,
                                           Instant requestTime, String remark,
                                           BigDecimal amount, Currency currency,
                                           BankId bankId, BankCardId bankCardId,
                                           MtRequestId mtRequestId) {
        return null;
    }
}
