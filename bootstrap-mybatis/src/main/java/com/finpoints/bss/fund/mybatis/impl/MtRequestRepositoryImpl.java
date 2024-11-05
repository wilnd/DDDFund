package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.mt.MtRequest;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MtRequestRepositoryImpl implements MtRequestRepository {

    @Override
    public MtRequestId nextId() {
        return null;
    }

    @Override
    public MtRequest save(MtRequest entity) {
        return null;
    }

    @Override
    public Collection<MtRequest> saveAll(Collection<MtRequest> entities) {
        return List.of();
    }

    @Override
    public MtRequest findById(MtRequestId requestId) {
        return null;
    }

    @Override
    public void delete(MtRequest entity) {

    }

    @Override
    public void deleteById(MtRequestId requestId) {

    }

    @Override
    public boolean existsById(MtRequestId requestId) {
        return false;
    }
}
