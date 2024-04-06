package com.czabala.myhome.util.security;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;

@Component
public class TokenGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateToken() {
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return new BigInteger(1, tokenBytes).toString(16);
    }

    public Timestamp generateExpirationDate() {
        return new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }
}
