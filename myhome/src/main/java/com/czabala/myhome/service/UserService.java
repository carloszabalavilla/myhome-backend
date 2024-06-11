package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dto.AuthenthicationRequest;
import com.czabala.myhome.domain.model.dto.UserDTO;

import java.util.Set;

public interface UserService extends Service<UserDTO> {
    UserDTO findByEmail(String email);

    Set<UserDTO> findByRole(String role);

    void startChangePassword(String email);

    UserDTO letChangePassword(String jwt);

    UserDTO changePassword(String jwt, AuthenthicationRequest auth);

    void resendConfirmation(String email);

    UserDTO confirmEmail(String jwt);

    UserDTO addAdmin(long id);

    UserDTO removeAdmin(long id);
}
