package com.czabala.myhome.util.validator;

import com.czabala.myhome.util.exception.InvalidFieldException;

public class EmailValidator {

    public static void validateEmail(String email) throws InvalidFieldException {
        if (email == null)
            throw new InvalidFieldException("La dirección de correo electrónico no puede estar vacía");
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
            throw new InvalidFieldException("La dirección de correo electrónico no es válida");
    }
}
