package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import com.czabala.myhome.util.exception.InvalidEmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        try {
            Set<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/user-id")
    public ResponseEntity<?> findUserById(@RequestParam(value = "id") long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/user-email")
    public ResponseEntity<?> findUserByEmail(@RequestParam(value = "email") String email) {
        try {
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/user-role")
    public ResponseEntity<?> findUserByRole(@RequestParam(value = "role") String role) {
        try {
            Set<User> users = userService.findByRole(role);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al buscar usuario: Rol no válido");
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.add(userDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("Error al crear usuario: Campos no válidos");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al crear usuario: El usuario ya existe");
        }
    }

    @PutMapping("/users/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            userService.update(userDTO);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("Error al actualizar usuario: Campos no válidos");
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/user")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
