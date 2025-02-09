package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import com.finpoints.bss.fund.domain.model.common.ExchangeableAmount;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WithdrawalOrderSubmitted extends DomainEvent {

    private final WithdrawalOrderNo withdrawalOrderNo;
    private final UserId userId;
    private final WalletId walletId;
    private final WalletType walletType;
    private final WithdrawalMethod withdrawalMethod;
    private final ExchangeableAmount amount;
    private final WithdrawalOrderStatus status;

    public WithdrawalOrderSubmitted(String appId, WithdrawalOrderNo withdrawalOrderNo,
                                    UserId userId, WalletId walletId, WalletType walletType,
                                    WithdrawalMethod withdrawalMethod,
                                    ExchangeableAmount amount,
                                    WithdrawalOrderStatus status) {
        super(appId);
        this.withdrawalOrderNo = withdrawalOrderNo;
        this.userId = userId;
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
