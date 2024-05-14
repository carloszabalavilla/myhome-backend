package com.czabala.myhome.util.validator;

import com.czabala.myhome.util.exception.InvalidFieldException;

public class PasswordValidator {

    public static void validatePassword(String password) {
        if (password == null)
            throw new InvalidFieldException("La contraseña no puede estar vacia");
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            throw new InvalidFieldException("La contraseña debe contener mayusculas, minusculas y un caracter especial");
    }
}
