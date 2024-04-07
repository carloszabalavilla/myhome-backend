package com.czabala.myhome.util.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordEncrypter {

    public static String encryptPassword(String password, String salt) {
        try {
            String passwordWithSalt = password + generateSalt();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(passwordWithSalt.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static boolean verifyPassword(String enteredPassword, String storedPassword, String salt) {
        String enteredPasswordHashBase64 = encryptPassword(enteredPassword, salt);
        assert enteredPasswordHashBase64 != null;
        return enteredPasswordHashBase64.equals(storedPassword);
    }
}
