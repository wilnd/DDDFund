package com.finpoints.bss.fund.mybatis;

import com.finpoints.bss.common.domain.model.Entity;

public interface EntityConverter<DE extends Entity, PE extends EntityAdapter> {

    DE toDomainEntity(PE persistenceEntity);

    PE toPersistenceEntity(DE domainEntity);
}
