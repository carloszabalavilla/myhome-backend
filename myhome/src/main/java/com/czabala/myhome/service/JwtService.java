package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    /***
     * Tiempo de expiración del token en minutos
     */
    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_TIME;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.prefix}")
    private String PREFIX;

    public static String decodeToken(String encodedJwt) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedJwt);
        return new String(decodedBytes);
    }

    public String generateToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRATION_TIME);
        return Jwts.builder()
                .claims(generateExtraClaims(user))
                .subject(user.getEmail())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .header().add(Header.TYPE, Header.JWT_TYPE)
                .and()
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateOneHourToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        return Jwts.builder()
                .claims(generateExtraClaims(user))
                .subject(user.getEmail())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .header().add(Header.TYPE, Header.JWT_TYPE)
                .and()
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);//SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey generateSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        if (token.startsWith(PREFIX)) {
            token = token.substring(PREFIX.length()).trim();
        }
        return Jwts
                .parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token.replace(PREFIX + " ", ""))
                .getPayload().getSubject();
    }

    public String encodeToken(String jwt) {
        return Base64.getUrlEncoder().encodeToString(jwt.getBytes());
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getFirstName() + " " + user.getLastName());
        claims.put("role", user.getRole().name());
        claims.put("permissions", user.getAuthorities());
        return claims;
    }


}
