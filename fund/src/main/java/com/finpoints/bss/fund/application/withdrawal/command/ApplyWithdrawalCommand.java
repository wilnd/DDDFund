package com.finpoints.bss.fund.application.withdrawal.command;

import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class ApplyWithdrawalCommand {

    private final String orderNo;
    private final String userId;
    private final String walletId;
    private final WalletType walletType;
    private final WithdrawalMethod withdrawalMethod;
    private final Instant requestTime;
    private final BigDecimal amount;
    private final String remark;
    private final String bankId;
    private final String bankCardId;
    private final String mtAccount;
    private final String serverId;
}
