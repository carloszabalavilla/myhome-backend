package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<User> findAllAdmin(){
        return userService.findByRole("ADMIN");
    }

    @GetMapping("/id")
    public User findAdminById(@PathParam("id") long id){
        return userService.findById(id);
    }

    @PostMapping
    public User createAdmin(UserDTO userDTO){
        userDTO.setRole("ADMIN");
        return userService.add(userDTO);
    }

    @PutMapping
    public User updateAdmin(UserDTO userDTO){
        return userService.update(userDTO);
    }

    @PatchMapping("/add")
    public User addAdmin(@PathParam("email") String email){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setRole("ADMIN");
        return userService.update(userDTO);
    }

    @PatchMapping("/remove")
    public User removeAdmin(@PathParam("email") String email){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setRole("USER");
        return userService.update(userDTO);
    }

    @DeleteMapping
    public void deleteAdmin(long id){
        userService.delete(id);
    }
}