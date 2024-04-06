package com.czabala.myhome.util.validator;

import com.czabala.myhome.util.exception.InvalidEmailException;

public class EmailValidator {

    public static void validateEmail(String email) throws InvalidEmailException {
        if (email == null)
            throw new InvalidEmailException("Dirección de correo electrónico no válida: " + email);
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
            throw new InvalidEmailException("Dirección de correo electrónico no válida: " + email);
    }
}
