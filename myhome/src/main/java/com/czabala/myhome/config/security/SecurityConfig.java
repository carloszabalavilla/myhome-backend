package com.czabala.myhome.config.security;

import com.czabala.myhome.config.security.filter.JwtAuthenticationFilter;
import com.czabala.myhome.util.security.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpMethod.DELETE;

/**
 * SecurityConfig is a configuration class that sets up the Spring Security filter chain.
 */
@EnableWebSecurity
@Component
public class SecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * This method configures the Spring Security filter chain.
     * It disables CSRF protection, sets the session creation policy to stateless, sets the authentication provider,
     * adds the JWT authentication filter before the username/password authentication filter,
     * and configures the authorization rules for various request matchers.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/auth/**", "/error")
                            .permitAll();
                    authConfig.requestMatchers("/user/**", "/task/**", "/family/**", "/finance/**")
                            .hasAnyAuthority(Permission.ALL_USER.name(), Permission.ALL_PERMISSION.name());
                    authConfig.requestMatchers("/admin/**")
                            .hasAuthority(Permission.ALL_PERMISSION.name());
                    authConfig.requestMatchers(DELETE)
                            .hasAuthority(Permission.ALL_PERMISSION.name());
                    authConfig.anyRequest()
                            .denyAll();
                });

        return http.build();
    }
}
