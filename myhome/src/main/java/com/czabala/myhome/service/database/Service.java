package com.czabala.myhome.service.database;

import java.util.Set;

public interface Service<T, U> {
    Set<T> findAll();

    T findById(long id);

    T add(U u);

    T update(U u);

    void delete(long id);
}
