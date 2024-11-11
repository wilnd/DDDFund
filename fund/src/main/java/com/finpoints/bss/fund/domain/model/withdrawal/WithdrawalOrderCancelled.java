package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import com.finpoints.bss.fund.domain.model.common.ExchangeableAmount;
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
    private final ExchangeableAmount amount;
    private final WithdrawalOrderStatus status;

    public WithdrawalOrderCancelled(String appId, WithdrawalOrderNo withdrawalOrderNo,
                                    WalletId walletId, WalletType walletType,
                                    WithdrawalMethod withdrawalMethod,
                                    ExchangeableAmount amount,
                                    WithdrawalOrderStatus status) {
        super(appId);
        this.withdrawalOrderNo = withdrawalOrderNo;
        this.walletId = walletId;
        this.walletType = walletType;
        this.withdrawalMethod = withdrawalMethod;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Withdrawal;
    }
}
