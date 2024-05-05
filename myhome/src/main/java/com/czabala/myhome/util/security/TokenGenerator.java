package com.czabala.myhome.util.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String SECRET = "8148621D5CDFDB754CFA38431C29E3ART874B2A3C4D5E6F7A8B9C0D1E2F3A4B5";

    public static String generateToken() {
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return new BigInteger(1, tokenBytes).toString(16);
    }

    public static Timestamp generateExpirationDate() {
        return new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }

    public static String getJWTToken(String username) {
        return "Bearer " + Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSigningKey()).compact();
    }

    public static Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static SecretKey getSecretKey() {
        //return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
