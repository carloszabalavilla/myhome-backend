package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
   @GetMapping("/users")
    public ResponseEntity<Set<User>> findAllUsers() {
        Set<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
