package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.wallet.FrozenTransaction;
import com.finpoints.bss.fund.domain.model.wallet.FrozenTransactionId;
import com.finpoints.bss.fund.domain.model.wallet.FrozenTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class FrozenTransactionRepositoryImpl implements FrozenTransactionRepository {

    @Override
    public FrozenTransactionId nextId() {
        return null;
    }

    @Override
    public FrozenTransaction save(FrozenTransaction entity) {
        return null;
    }

    @Override
    public Collection<FrozenTransaction> saveAll(Collection<FrozenTransaction> entities) {
        return List.of();
    }

    @Override
    public FrozenTransaction findById(FrozenTransactionId transactionId) {
        return null;
    }

    @Override
    public void delete(FrozenTransaction entity) {

    }

    @Override
    public void deleteById(FrozenTransactionId transactionId) {

    }

    @Override
    public boolean existsById(FrozenTransactionId transactionId) {
        return false;
    }

    @Override
    public FrozenTransaction findByIdemKey(String idemKey) {
        return null;
    }

}
