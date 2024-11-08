package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtServerId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;

import java.math.BigDecimal;
import java.time.Instant;

public interface WithdrawalMethodStrategy {

    WithdrawalOrder withdrawal(String appId, WithdrawalOrderNo orderNo, UserId userId,
                               WalletId walletId, WalletType walletType,
                               Instant requestTime, String remark,
                               BigDecimal amount, Currency currency,
                               BankId bankId, BankCardId bankCardId,
                               MtServerId serverId, String mtAccount);

}
