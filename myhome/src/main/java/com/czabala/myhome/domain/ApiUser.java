package com.czabala.myhome.domain;

import com.czabala.myhome.domain.model.enums.user.AccountStatus;

import java.sql.Date;

//@Entity(name = "api_user")
public class ApiUser {
    private long id;
    private String username;
    private String password;
    private String role;
    private String appName;
    private String domain;
    private Date creationDate;
    private Date lastAccess;
    private AccountStatus accountStatus;
    private String additionalInfo;
}