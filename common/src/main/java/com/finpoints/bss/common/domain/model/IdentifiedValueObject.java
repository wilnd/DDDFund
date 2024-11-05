package com.finpoints.bss.common.domain.model;

public class IdentifiedValueObject implements IdentifiedDomainObject {

    private final String id;

    public IdentifiedValueObject(String id) {
        this.id = id;
    }

    @Override
    public String delegateId() {
        return id;
    }
}
