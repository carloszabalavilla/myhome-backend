package com.czabala.myhome.util.exception;

import com.czabala.myhome.util.mapper.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<String> handleInvalidFieldException(InvalidFieldException ex) {
        return JsonObject.jsonMsgResponse(400, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return JsonObject.jsonMsgResponse(400, "Campo no valido: "
                + Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField()
                + " - " + ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return JsonObject.jsonMsgResponse(400, "Cuerpo de la peticion no valido");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        return JsonObject.jsonMsgResponse(401, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleAuthErrorException(BadCredentialsException ex) {
        return JsonObject.jsonMsgResponse(401, ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(EntityNotFoundException ex) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<String> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException ex
    ) {
        return JsonObject.jsonMsgResponse(404, ex.getMessage());
    }

    @ExceptionHandler(ConflictInDatabaseException.class)
    public ResponseEntity<String> handleConflictInDatabaseException(ConflictInDatabaseException ex) {
        return JsonObject.jsonMsgResponse(409, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex) {
        return JsonObject.jsonMsgResponse(500, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace();
        return JsonObject.jsonMsgResponse(500, ex.getMessage());
    }

    @ExceptionHandler(UnableToDeleteResource.class)
    public ResponseEntity<String> handleUnableToDeleteResource(UnableToDeleteResource ex) {
        return JsonObject.jsonMsgResponse(502, ex.getMessage());
    }
}
