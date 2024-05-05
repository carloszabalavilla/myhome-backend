package com.czabala.myhome.util.security;


import com.czabala.myhome.util.exception.ApiException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
//TODO remove class from project and use BCryptPasswordEncoder from Spring Security
public class PasswordEncryptor {

    public static String[] encryptPassword(String password, String inputSalt) {
        try {
            byte[] salt;
            if (inputSalt == null) {
                SecureRandom random = new SecureRandom();
                salt = new byte[16];
                random.nextBytes(salt);
            } else {
                salt = inputSalt.getBytes();
            }
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String[]{Arrays.toString(hash), Arrays.toString(salt)};
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ApiException("Error al encriptar la contraseña" + e);
        }
    }

    public static boolean checkPassword(String password, String encryptedPassword, String salt) {
        return encryptPassword(password, salt)[0].equals(encryptedPassword);
    }
}