package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.UserService;
import com.czabala.myhome.util.mapper.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

/**
 * UserController is a REST controller that handles user related requests.
 * It provides endpoints for finding all users, finding a user by id, email, role, or token,
 * creating a user, updating a user, and deleting a user.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Handles a GET request to find all users.
     *
     * @return a ResponseEntity containing a Set of all Users
     */
    @GetMapping
    @PreAuthorize("@resourceAccess.admin()")
    public ResponseEntity<Set<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Handles a GET request to find a user by id.
     *
     * @param id the id of the user to find
     * @return a ResponseEntity containing the User with the specified id
     */
    @GetMapping("/id")
    @PreAuthorize("@resourceAccess.user(#id)")
    public ResponseEntity<UserDTO> findUserById(@RequestParam long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Handles a GET request to find a user by email.
     *
     * @param email the email of the user to find
     * @return a ResponseEntity containing the User with the specified email
     */
    @GetMapping("/email")
    @PreAuthorize("@resourceAccess.email(#email)")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    /**
     * Handles a GET request to find users by role.
     *
     * @param role the role of the users to find
     * @return a ResponseEntity containing a Set of Users with the specified role
     */
    @GetMapping("/role")
    @PreAuthorize("@resourceAccess.admin()")
    public ResponseEntity<Set<UserDTO>> findUserByRole(@RequestParam String role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

    /**
     * Handles a POST request to create a user.
     *
     * @param userDTO the UserDTO containing the new user's information
     * @return a ResponseEntity containing the created User
     */
    @PostMapping()
    @PreAuthorize("@resourceAccess.admin()")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.add(userDTO));
    }

    /**
     * Handles a PUT request to update a user.
     *
     * @param userDTO the UserDTO containing the updated user's information
     * @return a ResponseEntity containing the updated User
     */
    @PutMapping
    @PreAuthorize("@resourceAccess.user(#userDTO.id)")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    /**
     * Handles a DELETE request to delete a user.
     *
     * @param id the id of the user to delete
     * @return a ResponseEntity containing a message indicating that the user has been deleted
     */
    @DeleteMapping
    @PreAuthorize("@resourceAccess.user(#id)")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") long id) {
        userService.delete(id);
        return JsonObject.jsonMsgResponse(200, "Usuario eliminado");
    }
}
