package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;
    private final String endpoint = "/user";
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(endpoint)
    public ResponseEntity<?> findAllUsers() {
        Set<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(endpoint+"/id")
    public ResponseEntity<?> findUserById(@RequestParam(value = "id") long id, @RequestParam(value = "token") String token) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(endpoint+"/email")
    public ResponseEntity<?> findUserByEmail(@RequestParam(value = "email") String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping(endpoint+"/role")
    public ResponseEntity<?> findUserByRole(@RequestParam(value = "role") String role) {
        Set<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping(endpoint+"/token")
    public ResponseEntity<?> findUserByToken(@RequestParam(value = "token") String token) {
        User user = userService.findByToken(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping(endpoint)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.add(userDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping(endpoint)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    @DeleteMapping(endpoint)
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") long id) {
        userService.delete(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }
}
