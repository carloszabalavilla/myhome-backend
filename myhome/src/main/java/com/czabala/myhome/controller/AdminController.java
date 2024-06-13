package com.czabala.myhome.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController is a REST controller that handles admin related requests.
 * It provides endpoints for finding all admins, adding a user as an admin, removing admin role from a user, and deleting an admin.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
//    private final UserService userService;
//
//    /**
//     * Constructs a new AdminController with the specified UserService.
//     *
//     * @param userService the UserService to be used by the AdminController
//     */
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * Handles a GET request to find all admins.
//     *
//     * @return a ResponseEntity containing a Set of all Users with the role "ADMIN"
//     */
//    @GetMapping
//    public ResponseEntity<Set<UserDTO>> findAllAdmin() {
//        Set<User> admins = userService.findByRole(Role.ADMINISTRATOR);
//        return ResponseEntity.ok(admins);
//    }
//
//    /**
//     * Handles a PATCH request to add a user as an admin.
//     *
//     * @param id the id of the user to be added as an admin
//     * @return the User that has been added as an admin
//     */
//    @PatchMapping("/add")
//    public ResponseEntity<?> addAdmin(@RequestParam long id) {
//        return userService.addAdmin(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
//    }
//
//    /**
//     * Handles a PATCH request to remove the admin role from a user.
//     *
//     * @param id the id of the user to remove the admin role from
//     * @return the User that has had the admin role removed
//     */
//    @PatchMapping("/remove")
//    public ResponseEntity<?> removeAdmin(@RequestParam long id) {
//        return userService.removeAdmin(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
//    }
//
//    /**
//     * Handles a DELETE request to delete an admin.
//     *
//     * @param id the id of the admin to delete
//     */
//    @DeleteMapping
//    public void deleteAdmin(@RequestParam long id) {
//        userService.delete(id);
//    }
}