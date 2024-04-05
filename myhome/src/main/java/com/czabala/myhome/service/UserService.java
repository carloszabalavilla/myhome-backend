package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;

public interface UserService extends Service<User> {
    User findUserByEmail(String email);
    User findUserByRole(String role);

    User mapToUser(UserDTO userDTO);
}
