package com.czabala.myhome.service.database;

import java.util.Set;

public interface Service<T,U> {
    Set<T> findAll();
    T findById(long id);
    T add(U t) throws IllegalAccessException;
    T update(U t) throws IllegalAccessException;
    void delete(long id);
}
