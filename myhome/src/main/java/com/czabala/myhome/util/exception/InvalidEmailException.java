package com.czabala.myhome.util.exception;

public class InvalidEmailException extends ApiException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
