package com.finpoints.bss.common.domain.model;

import lombok.AccessLevel;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter(AccessLevel.NONE)
public class AbstractId implements Identity, Serializable {

    private final String id;

    protected AbstractId(String anId) {
        this.id = anId;
    }

    @Override
    public String rawId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractId that = (AbstractId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                '}';
    }
}
