package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethodStrategy;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawalToWireStrategy implements WithdrawalMethodStrategy {

    @Override
    public WithdrawalOrder withdrawal(UserId userId, WalletId walletId, WalletType walletType,
                                      WithdrawalMethod withdrawalMethod, BigDecimal amount,
                                      Currency originalCurrency, String remark, BankId bankId, BankCardId bankCardId,
                                      WithdrawalOrderNo withdrawalOrderNo, String mtAccount, String serverId) {
        return null;
    }
}
