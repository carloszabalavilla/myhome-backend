package com.czabala.myhome.util.exception;

import com.czabala.myhome.util.HttpCode;

@HttpCode(404)
public class TaskNotFoundException extends ApiException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
