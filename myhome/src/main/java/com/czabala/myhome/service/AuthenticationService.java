package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dto.AuthenticationRequest;
import com.czabala.myhome.domain.model.dto.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
