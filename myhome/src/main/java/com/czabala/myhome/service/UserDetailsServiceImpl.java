package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.service.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email){
        return userService.findByEmail(email);
    }
}