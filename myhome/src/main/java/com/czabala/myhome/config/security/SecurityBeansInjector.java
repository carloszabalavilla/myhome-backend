package com.czabala.myhome.config.security;

import com.czabala.myhome.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SecurityBeansInjector is a configuration class that provides Spring Security beans.
 */
@Component
public class SecurityBeansInjector {

    /**
     * This method provides the AuthenticationManager bean.
     * The AuthenticationManager is responsible for passing requests through the authentication chain.
     *
     * @return the AuthenticationManager bean
     * @throws Exception if unable to get the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method provides the AuthenticationProvider bean.
     * The AuthenticationProvider is responsible for validating the authentication.
     * In this case, DaoAuthenticationProvider is used with a user details service and a password encoder.
     *
     * @return the AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(email -> userRepository.findByEmail(email).orElseThrow());
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
}
