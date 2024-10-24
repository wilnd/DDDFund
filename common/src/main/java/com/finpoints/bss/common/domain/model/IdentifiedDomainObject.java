package com.finpoints.bss.common.domain.model;

import java.io.Serializable;

public abstract class IdentifiedDomainObject implements Serializable {

    /**
     * 委派标识
     */
    private Long id;

    protected void setId(Long anId) {
        this.id = anId;
    }

    protected Long getId() {
        return id;
    }

    public Long delegateId() {
        return this.getId();
    }
}
