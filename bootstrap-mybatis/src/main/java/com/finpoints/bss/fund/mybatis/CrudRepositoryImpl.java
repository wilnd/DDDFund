package com.finpoints.bss.fund.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finpoints.bss.common.domain.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CrudRepositoryImpl<DE extends Entity, DID extends Identity, PE extends EntityAdapter>
        implements CrudRepository<DE, DID> {

    private final BaseMapper<PE> mapper;
    private final EntityConverter<DE, PE> entityConverter;

    protected CrudRepositoryImpl(BaseMapper<PE> mapper,
                                 EntityConverter<DE, PE> entityConverter) {
        this.mapper = mapper;
        this.entityConverter = entityConverter;
    }

    @Override
    public DE save(DE entity) {
        PE persistenceEntity = convertToPersistence(entity);
        if (persistenceEntity == null) {
            return null;
        }
        if (entity instanceof AggregateRoot aggregateRoot) {
            List<DomainEvent> events = aggregateRoot.pullDomainEvents();
            DomainEventPublisher.instance().publishAll(events);
        }

        int res;
        if (persistenceEntity.id() == null) {
            res = mapper.insert(persistenceEntity);
        } else {
            res = mapper.updateById(persistenceEntity);
        }
        if (res == 0) {
            throw new RuntimeException("save failed");
        }
        return convertToDomain(persistenceEntity);
    }

    @Override
    public Collection<DE> saveAll(Collection<DE> entities) {
        List<DE> result = new ArrayList<>();
        for (DE entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public DE findById(DID did) {
        QueryWrapper<PE> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(domainIdField(), did.rawId());

        return convertToDomain(mapper.selectOne(queryWrapper));
    }

    @Override
    public boolean existsById(DID did) {
        QueryWrapper<PE> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(domainIdField(), did.rawId());

        return mapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void delete(DE entity) {
        PE persistenceEntity = convertToPersistence(entity);
        if (persistenceEntity == null) {
            return;
        }

        // soft delete
        if (persistenceEntity.softDelete()) {
            mapper.updateById(persistenceEntity);
        } else {
            mapper.deleteById(persistenceEntity.id());
        }
    }

    @Override
    public void deleteById(DID id) {
        DE domainEntity = findById(id);
        if (domainEntity == null) {
            return;
        }
        delete(domainEntity);
    }

    protected PE convertToPersistence(DE domainEntity) {
        PE persistenceEntity = entityConverter.toPersistenceEntity(domainEntity);
        if (persistenceEntity == null) {
            return null;
        }
        persistenceEntity.copyFrom(domainEntity);
        return persistenceEntity;
    }

    protected DE convertToDomain(PE persistenceEntity) {
        DE domainEntity = entityConverter.toDomainEntity(persistenceEntity);
        if (domainEntity == null) {
            return null;
        }
        persistenceEntity.copyTo(domainEntity);
        return domainEntity;
    }

    protected abstract String domainIdField();
}
