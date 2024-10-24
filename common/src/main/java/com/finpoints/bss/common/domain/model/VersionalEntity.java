package com.finpoints.bss.common.domain.model;

import lombok.Getter;

@Getter
public class VersionalEntity extends Entity {

    private final Integer version;

    public VersionalEntity() {
        super();
        this.version = 1;
    }
}
