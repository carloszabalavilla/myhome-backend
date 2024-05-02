package com.czabala.myhome.util.exception;

public class TaskNotFoundException extends ApiException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
