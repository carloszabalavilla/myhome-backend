package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dto.AuthenticationRequest;
import com.czabala.myhome.domain.model.dto.UserDTO;

import java.util.Set;

public interface UserService extends Service<UserDTO> {
    UserDTO findByEmail(String email);

    Set<UserDTO> findByRole(String role);

    boolean checkUser(String email);

    void startChangePassword(String email);

    UserDTO letChangePassword(String jwt);

    UserDTO changePassword(String jwt, AuthenticationRequest auth);

    void resendConfirmation(String email);

    UserDTO confirmEmail(String jwt);

    UserDTO addAdmin(long id);

    UserDTO removeAdmin(long id);
}
