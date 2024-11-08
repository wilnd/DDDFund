package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.strategy.MtWalletWithdrawalStrategy;
import com.finpoints.bss.fund.domain.model.withdrawal.strategy.TradingWalletWithdrawalStrategy;
import com.finpoints.bss.fund.domain.model.withdrawal.strategy.WithdrawalToBankStrategy;
import com.finpoints.bss.fund.domain.model.withdrawal.strategy.WithdrawalToWireStrategy;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalStrategyFactory {

    private final WithdrawalToBankStrategy withdrawalToBankStrategy;
    private final WithdrawalToWireStrategy withdrawalToWireStrategy;

    public WithdrawalStrategyFactory(WithdrawalToBankStrategy withdrawalToBankStrategy,
                                     WithdrawalToWireStrategy withdrawalToWireStrategy) {
        this.withdrawalToBankStrategy = withdrawalToBankStrategy;
        this.withdrawalToWireStrategy = withdrawalToWireStrategy;
    }

    public WalletWithdrawalStrategy walletStrategy(WalletType walletType) {
        return switch (walletType) {
            case MT -> new MtWalletWithdrawalStrategy();
            case TRADING -> new TradingWalletWithdrawalStrategy();
            default -> throw new IllegalArgumentException("Unsupported wallet type: " + walletType);
        };
    }

    public WithdrawalMethodStrategy methodStrategy(WithdrawalMethod withdrawalMethod) {
        switch (withdrawalMethod) {
            case BANK -> {
                return withdrawalToBankStrategy;
            }
            case WIRE -> {
                return withdrawalToWireStrategy;
            }
            default -> throw new IllegalArgumentException("Unsupported withdrawal method: " + withdrawalMethod);
        }
    }

}
