package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.CrudRepository;
import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.common.domain.model.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractRepository<DE extends Entity, DID extends Identity, PE extends JpaEntityBase, PID>
        implements CrudRepository<DE, DID> {

    private final EntityConverter<DE, PE> entityConverter;
    protected final JpaRepository<PE, PID> jpaRepository;

    public AbstractRepository(EntityConverter<DE, PE> entityConverter,
                              JpaRepository<PE, PID> jpaRepository) {
        this.entityConverter = entityConverter;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public DE save(DE entity) {
        PE persistenceEntity = convertToPersistence(entity);
        if (persistenceEntity == null) {
            return null;
        }
        return convertToDomain(jpaRepository.save(persistenceEntity));
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
