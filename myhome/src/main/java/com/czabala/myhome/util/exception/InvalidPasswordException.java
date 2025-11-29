package com.czabala.myhome.util.exception;

public class InvalidPasswordException extends ApiException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
