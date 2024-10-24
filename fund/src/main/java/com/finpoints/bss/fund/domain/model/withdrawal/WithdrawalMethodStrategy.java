package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;

import java.math.BigDecimal;

public interface WithdrawalMethodStrategy {

    WithdrawalOrder withdrawal(UserId userId, WalletId walletId, WalletType walletType,
                               WithdrawalMethod withdrawalMethod, BigDecimal amount,
                               Currency originalCurrency, String remark, BankId bankId, BankCardId bankCardId,
                               WithdrawalOrderNo withdrawalOrderNo, String mtAccount, String serverId);

}
