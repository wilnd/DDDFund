package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;

import java.math.BigDecimal;

public interface WalletWithdrawalStrategy {

    boolean satisfied(WalletId walletId, String mtAccount, String serverId, BigDecimal amount);

    Currency getCurrency(WalletId walletId, String mtAccount, String serverId);
}
