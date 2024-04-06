package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.UserRole;

import java.util.Set;

public interface UserService extends Service<User> {
    User findByEmail(String email);

    Set<User> findByRole(UserRole role);
    User mapToUser(UserDTO userDTO);

    void processConfirmationToken(String token);

}
