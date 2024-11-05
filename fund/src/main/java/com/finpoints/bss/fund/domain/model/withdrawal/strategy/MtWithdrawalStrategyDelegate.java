package com.finpoints.bss.fund.domain.model.withdrawal.strategy;

import com.finpoints.bss.fund.domain.model.common.BankCardId;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtRequest;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestRepository;
import com.finpoints.bss.fund.domain.model.mt.MtServerId;
import com.finpoints.bss.fund.domain.model.mt.command.MtWithdrawalCommand;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethodStrategy;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public abstract class MtWithdrawalStrategyDelegate implements WithdrawalMethodStrategy {

    private final MtRequestRepository mtRequestRepository;

    protected MtWithdrawalStrategyDelegate(MtRequestRepository mtRequestRepository) {
        this.mtRequestRepository = mtRequestRepository;
    }

    @Override
    public WithdrawalOrder withdrawal(WithdrawalOrderNo orderNo, UserId userId,
                                      WalletId walletId, WalletType walletType,
                                      Instant requestTime, String remark,
                                      BigDecimal amount, Currency currency,
                                      BankId bankId, BankCardId bankCardId,
                                      MtServerId serverId, String mtAccount) {
        if (serverId == null) {
            return doWithdrawal(orderNo, userId, walletId, walletType, requestTime, remark,
                    amount, currency, bankId, bankCardId, null);
        }

        // 创建MT出金请求
        BigDecimal mtAmount = amount.setScale(2, RoundingMode.DOWN);
        MtRequest mtRequest = new MtRequest(
                mtRequestRepository.nextId(),
                serverId,
                mtAccount,
                new MtWithdrawalCommand(amount, "wbu withdrawal -" + mtAmount)
        );
        mtRequestRepository.save(mtRequest);
        
        return doWithdrawal(orderNo, userId, walletId, walletType, requestTime, remark,
                amount, currency, bankId, bankCardId, mtRequest.getRequestId());
    }

    /**
     * 执行出金
     */
    protected abstract WithdrawalOrder doWithdrawal(WithdrawalOrderNo orderNo, UserId userId,
                                                    WalletId walletId, WalletType walletType,
                                                    Instant requestTime, String remark,
                                                    BigDecimal amount, Currency currency,
                                                    BankId bankId, BankCardId bankCardId,
                                                    MtRequestId mtRequestId);

}
