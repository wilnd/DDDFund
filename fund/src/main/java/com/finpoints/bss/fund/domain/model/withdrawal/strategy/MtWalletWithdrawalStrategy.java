package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.withdrawal.WalletWithdrawalStrategy;

import java.math.BigDecimal;

public class MtWalletWithdrawalStrategy implements WalletWithdrawalStrategy {


    @Override
    public boolean satisfied(WalletId walletId, String mtAccount, String serverId, BigDecimal amount) {
        return false;
    }

    @Override
    public Currency getCurrency(WalletId walletId, String mtAccount, String serverId) {
        return null;
    }
}
