package com.czabala.myhome.util.validator;

import com.czabala.myhome.util.exception.InvalidPasswordException;

public class PasswordValidator {

        public static void validatePassword(String password) {
            if (password == null)
                throw new InvalidPasswordException("Contraseña no válida: " + password);
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
                throw new InvalidPasswordException("Contraseña no válida: " + password);
        }
}
