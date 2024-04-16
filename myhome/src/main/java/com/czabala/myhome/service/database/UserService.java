package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;

import java.util.Set;

public interface UserService extends Service<User,UserDTO> {
    User findByEmail(String email);
    Set<User> findByRole(String role);
    void processConfirmationToken(String token);
}
