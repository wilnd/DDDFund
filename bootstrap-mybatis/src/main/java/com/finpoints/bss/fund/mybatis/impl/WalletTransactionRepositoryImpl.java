package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.wallet.WalletTransaction;
import com.finpoints.bss.fund.domain.model.wallet.WalletTransactionId;
import com.finpoints.bss.fund.domain.model.wallet.WalletTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class WalletTransactionRepositoryImpl implements WalletTransactionRepository {
    @Override
    public WalletTransactionId nextId() {
        return null;
    }

    @Override
    public WalletTransaction save(WalletTransaction entity) {
        return null;
    }

    @Override
    public Collection<WalletTransaction> saveAll(Collection<WalletTransaction> entities) {
        return List.of();
    }

    @Override
    public WalletTransaction findById(WalletTransactionId walletTransactionId) {
        return null;
    }

    @Override
    public void delete(WalletTransaction entity) {

    }

    @Override
    public void deleteById(WalletTransactionId walletTransactionId) {

    }

    @Override
    public boolean existsById(WalletTransactionId walletTransactionId) {
        return false;
    }
}
