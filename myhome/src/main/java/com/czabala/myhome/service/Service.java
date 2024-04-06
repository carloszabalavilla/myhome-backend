package com.czabala.myhome.service;

import java.util.Set;

public interface Service<T> {
    Set<T> findAll();
    T findById(long id);
    T add(T t);

    T update(T t);
    void delete(long id);
}
