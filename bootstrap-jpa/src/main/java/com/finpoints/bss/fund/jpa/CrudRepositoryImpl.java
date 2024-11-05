package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CrudRepositoryImpl<DE extends Entity, DID extends Identity, PE extends JpaEntityBase>
        implements CrudRepository<DE, DID> {

    private final JpaEntityConverter<DE, PE> entityConverter;
    protected final JpaRepository<PE, Long> jpaRepository;

    public CrudRepositoryImpl(JpaEntityConverter<DE, PE> entityConverter,
                              JpaRepository<PE, Long> jpaRepository) {
        this.entityConverter = entityConverter;
        this.jpaRepository = jpaRepository;
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
        return convertToDomain(jpaRepository.save(persistenceEntity));
    }

    @Override
    public Collection<DE> saveAll(Collection<DE> entities) {
        List<PE> persistenceEntities = new ArrayList<>();
        for (DE entity : entities) {
            PE persistenceEntity = convertToPersistence(entity);
            if (persistenceEntity == null) {
                return null;
            }
            persistenceEntities.add(persistenceEntity);
            if (entity instanceof AggregateRoot aggregateRoot) {
                List<DomainEvent> events = aggregateRoot.pullDomainEvents();
                DomainEventPublisher.instance().publishAll(events);
            }
        }
        jpaRepository.saveAll(persistenceEntities);
        return persistenceEntities.stream()
                .map(this::convertToDomain)
                .toList();
    }

    @Override
    public void delete(DE entity) {
        PE persistenceEntity = convertToPersistence(entity);
        if (persistenceEntity == null) {
            return;
        }
        jpaRepository.delete(persistenceEntity);
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
}
