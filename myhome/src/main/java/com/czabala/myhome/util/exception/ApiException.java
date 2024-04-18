package com.czabala.myhome.util.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
