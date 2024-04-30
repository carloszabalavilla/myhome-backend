package com.czabala.myhome.service;

import org.springframework.stereotype.Service;

@Service
public interface DatabaseService {
    void loadData();

    void deleteData();
}
