package com.czabala.myhome.config.security.filter;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter that intercepts each request once and checks for the presence of a JWT token in the Authorization header.
 * If a token is present, it validates the token and sets up the Spring Security context with the user details from the token.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${security.jwt.prefix}")
    private String PREFIX;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method is called for every request. It checks for the presence of a JWT token in the Authorization header.
     * If a token is present, it validates the token and sets up the Spring Security context with the user details from the token.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(PREFIX + " ")) {
            try {
                Claims claims = validateToken(authHeader);
                if (claims != null) {
                    setUpSpringAuthentication(claims);
                }
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "El token ha expirado");
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * This method validates the JWT token.
     *
     * @param auth the JWT token
     * @return the claims from the JWT token if it is valid, null otherwise
     */
    private Claims validateToken(String auth) {

        return Jwts
                .parser()
                .verifyWith(jwtService.generateSecretKey())
                .build()
                .parseSignedClaims(auth.replace(PREFIX + " ", ""))
                .getPayload();
    }

    /**
     * This method sets up the Spring Security context with the user details from the claims.
     *
     * @param claims the claims from the JWT token
     */
    private void setUpSpringAuthentication(Claims claims) {
        User user = userRepository.findByEmail(claims.getSubject()).orElseThrow();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities()));
    }
}