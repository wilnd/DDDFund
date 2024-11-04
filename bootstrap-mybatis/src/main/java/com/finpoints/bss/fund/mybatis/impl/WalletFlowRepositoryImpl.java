package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.wallet.WalletFlow;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowId;
import com.finpoints.bss.fund.domain.model.wallet.WalletFlowRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class WalletFlowRepositoryImpl implements WalletFlowRepository {
    @Override
    public WalletFlowId nextId() {
        return null;
    }

    @Override
    public WalletFlow save(WalletFlow entity) {
        return null;
    }

    @Override
    public Collection<WalletFlow> saveAll(Collection<WalletFlow> entities) {
        return List.of();
    }

    @Override
    public WalletFlow findById(WalletFlowId walletFlowId) {
        return null;
    }

    @Override
    public void delete(WalletFlow entity) {

    }

    @Override
    public void deleteById(WalletFlowId walletFlowId) {

    }

    @Override
    public boolean existsById(WalletFlowId walletFlowId) {
        return false;
    }
}
