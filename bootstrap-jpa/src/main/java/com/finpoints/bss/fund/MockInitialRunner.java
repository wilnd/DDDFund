package com.finpoints.bss.fund;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.common.UserRole;
import com.finpoints.bss.fund.domain.model.wallet.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MockInitialRunner implements CommandLineRunner {

    private final WalletRepository walletRepository;
    private final WalletFlowRepository walletFlowRepository;

    public MockInitialRunner(WalletRepository walletRepository, WalletFlowRepository walletFlowRepository) {
        this.walletRepository = walletRepository;
        this.walletFlowRepository = walletFlowRepository;
    }

    @Override
    public void run(String... args) {
        Wallet wallet = new Wallet(
                "mock-app-1",
                new WalletId("mock-wallet-1"),
                UserRole.Client,
                new UserId("mock-user-1"),
                Currency.USD,
                WalletType.Trading,
                true
        );
        WalletFlow flow = wallet.increase(
                walletFlowRepository.nextId(),
                WalletFlowType.Deposit,
                new BigDecimal("1000"),
                "mock-deposit-order-1",
                "mock deposit"
        );
        walletRepository.save(wallet);
        walletFlowRepository.save(flow);
    }
}
