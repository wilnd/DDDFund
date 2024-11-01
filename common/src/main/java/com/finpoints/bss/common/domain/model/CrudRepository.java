package com.finpoints.bss.common.domain.model;

import java.util.Collection;

public interface CrudRepository<T extends Entity, ID extends Identity> {

    ID nextId();

    T save(T entity);

    Collection<T> saveAll(Collection<T> entities);

    T findById(ID id);

    void delete(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
