package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public User modify(long id, User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByRole(String role) {
        return null;
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        return null;
    }
}
