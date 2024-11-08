package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.wallet.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class FrozenFlowRepositoryImpl implements FrozenFlowRepository {

    @Override
    public FrozenFlowId nextId() {
        return null;
    }

    @Override
    public FrozenFlow save(FrozenFlow entity) {
        return null;
    }

    @Override
    public Collection<FrozenFlow> saveAll(Collection<FrozenFlow> entities) {
        return List.of();
    }

    @Override
    public FrozenFlow findById(FrozenFlowId transactionId) {
        return null;
    }

    @Override
    public void delete(FrozenFlow entity) {

    }

    @Override
    public void deleteById(FrozenFlowId transactionId) {

    }

    @Override
    public boolean existsById(FrozenFlowId transactionId) {
        return false;
    }

    @Override
    public FrozenFlow orderFlow(WalletId walletId, FrozenType freezeType, String orderNo) {
        return null;
    }
}
