package com.finpoints.bss.fund;

import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.Wallet;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MockInitialRunner implements CommandLineRunner {

    private final WalletRepository walletRepository;

    public MockInitialRunner(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Wallet wallet = new Wallet(
                new WalletId("mock-wallet-1"),
                new UserId("mock-user-1"),
                Currency.USD,
                WalletType.Trading,
                true,
                new BigDecimal("1000.00"),
                BigDecimal.ZERO,
                new BigDecimal("1000.00"),
                new BigDecimal("1000.00")
        );
        walletRepository.save(wallet);
    }
}
