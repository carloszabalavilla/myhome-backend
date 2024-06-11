package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.AuthenthicationRequest;
import com.czabala.myhome.domain.model.dto.AuthenthicationResponse;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.util.exception.ConflictInDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthenthicationResponse login(AuthenthicationRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        if (!new BCryptPasswordEncoder().matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales invalidas");
        } else if (!user.isConfirmed()) {
            throw new ConflictInDatabaseException("Usuario no confirmado");
        }
        String jwt = jwtService.generateToken(user);

        return new AuthenthicationResponse(jwt);
    }
}
