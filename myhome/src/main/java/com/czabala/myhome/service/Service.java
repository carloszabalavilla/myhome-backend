package com.czabala.myhome.service;

import java.util.Set;

public interface Service<U> {
    Set<U> findAll();

    U findById(long id);

    U add(U u);

    U update(U u);

    void delete(long id);

}
