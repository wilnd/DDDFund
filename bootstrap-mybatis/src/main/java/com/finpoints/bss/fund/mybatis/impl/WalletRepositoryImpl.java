package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.wallet.Wallet;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    @Override
    public WalletId nextId() {
        return null;
    }

    @Override
    public Wallet save(Wallet entity) {
        return null;
    }

    @Override
    public Collection<Wallet> saveAll(Collection<Wallet> entities) {
        return List.of();
    }

    @Override
    public Wallet findById(WalletId walletId) {
        return null;
    }

    @Override
    public void delete(Wallet entity) {

    }

    @Override
    public void deleteById(WalletId walletId) {

    }

    @Override
    public boolean existsById(WalletId walletId) {
        return false;
    }

    @Override
    public Wallet walletOf(WalletId walletId, WalletType walletType) {
        return null;
    }

}
