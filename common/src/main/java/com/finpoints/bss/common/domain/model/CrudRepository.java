package com.finpoints.bss.common.domain.model;

public interface CrudRepository<T extends Entity, ID extends Identity> {

    ID nextId();

    T save(T entity);

    T findById(ID id);

    void delete(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
