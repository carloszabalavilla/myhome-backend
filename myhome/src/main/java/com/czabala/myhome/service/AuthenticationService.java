package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dto.AuthenthicationRequest;
import com.czabala.myhome.domain.model.dto.AuthenthicationResponse;

public interface AuthenticationService {

    AuthenthicationResponse login(AuthenthicationRequest authenthicationRequest);
}
