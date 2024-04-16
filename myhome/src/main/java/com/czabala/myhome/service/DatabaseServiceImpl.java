package com.czabala.myhome.service;

import com.czabala.myhome.service.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Autowired
    private UserService userService;

    @Override
    public void loadData() {
        System.out.println("Loading data from database...");

    }

    @Override
    public void deleteData() {
        System.out.println("Not implemented yet.");
    }
}
