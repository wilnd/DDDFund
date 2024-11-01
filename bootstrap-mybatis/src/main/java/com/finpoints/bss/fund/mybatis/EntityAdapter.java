package com.finpoints.bss.fund.mybatis;

import com.finpoints.bss.common.domain.model.Entity;

import java.io.Serializable;

public interface EntityAdapter {

    Serializable id();

    default boolean softDelete() {
        return false;
    }

    void copyFrom(Entity entity);

    void copyTo(Entity entity);
}
