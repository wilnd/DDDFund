package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WithdrawalOrderCancelled extends DomainEvent {

    private final WithdrawalOrderNo withdrawalOrderNo;
    private final WalletId walletId;
    private final WalletType walletType;
    private final WithdrawalMethod withdrawalMethod;
    private final BigDecimal exchangeRate;
    private final BigDecimal amount;
    private final WithdrawalOrderStatus status;

    public WithdrawalOrderCancelled(WithdrawalOrderNo withdrawalOrderNo,
                                    WalletId walletId, WalletType walletType,
                                    WithdrawalMethod withdrawalMethod,
                                    BigDecimal exchangeRate, BigDecimal amount,
                                    WithdrawalOrderStatus status) {
        this.withdrawalOrderNo = withdrawalOrderNo;
        this.walletId = walletId;
        this.walletType = walletType;
        this.withdrawalMethod = withdrawalMethod;
        this.exchangeRate = exchangeRate;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Withdrawal;
    }
}
