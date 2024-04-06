package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.domain.model.enums.UserRole;
import com.czabala.myhome.service.UserService;
import com.czabala.myhome.util.exception.InvalidEmailException;
import com.czabala.myhome.util.validator.EmailValidator;
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
        Set<User> users = userService.findAll();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/users/userid")
    public ResponseEntity<?> findUserById(@RequestParam(value = "id") String id) {
        try {
            long idL = Long.parseLong(id);
            User user = userService.findById(idL);
            if (user.getId() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(user);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error al buscar usuario: ID no válido");
        }
    }

    @GetMapping("/users/useremail")
    public ResponseEntity<?> findUserByEmail(@RequestParam(value = "email") String email) {
        try {
            EmailValidator.validateEmail(email);
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/userrole")
    public ResponseEntity<?> findUserByRole(@RequestParam(value = "role") String role) {
        try {
            UserRole userRole = UserRole.valueOf(role);
            Set<User> users = userService.findByRole(userRole);
            if (users == null || users.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(users);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al buscar usuario: Rol no válido");
        }
    }

    @PostMapping("/users/user")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null || userDTO.hasNotNullOrEmpty()) {
            return ResponseEntity.badRequest().body("Error al crear usuario: Datos nulos");
        } else {
            User newUser = userService.mapToUser(userDTO);
            if (userService.add(newUser) == null) {
                return ResponseEntity.badRequest().body("Error al crear usuario: Email ya registrado");
            }
            return ResponseEntity.ok("Usuario creado exitosamente");
        }
    }

    @PutMapping("/users/user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        User user = userService.mapToUser(userDTO);
        if (userService.update(user) == null) {
            return ResponseEntity.badRequest().body("Error al actualizar usuario: El usuario no existe");
        }
        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    @DeleteMapping("/users/user")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") String id) {
        try {
            long idL = Long.parseLong(id);
            userService.delete(idL);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error al eliminar usuario: ID no válido");
        }
    }
}
