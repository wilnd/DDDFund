package com.finpoints.bss.fund.jpa;

import com.finpoints.bss.common.domain.model.Entity;

public interface EntityConverter<DE extends Entity, PE extends JpaEntityBase> {

    DE toDomainEntity(PE persistenceEntity);

    PE toPersistenceEntity(DE domainEntity);
}
