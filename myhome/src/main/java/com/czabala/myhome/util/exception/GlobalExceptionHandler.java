package com.czabala.myhome.util.exception;

import com.czabala.myhome.util.mapper.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<String> handleInvalidFieldException(InvalidFieldException ex) {
        return JsonObject.jsonMsgResponse(400, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(EntityNotFoundException ex) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(AuthErrorException.class)
    public ResponseEntity<String> handleAuthErrorException(AuthErrorException ex) {
        return JsonObject.jsonMsgResponse(401, ex.getMessage());
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<String> handleTokenValidationException(TokenValidationException ex) {
        return JsonObject.jsonMsgResponse(403, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex) {
        return JsonObject.jsonMsgResponse(500, ex.getMessage());
    }

    @ExceptionHandler(UnableToDeleteResource.class)
    public ResponseEntity<String> handleUnableToDeleteResource(UnableToDeleteResource ex) {
        return JsonObject.jsonMsgResponse(502, ex.getMessage());
    }
}
