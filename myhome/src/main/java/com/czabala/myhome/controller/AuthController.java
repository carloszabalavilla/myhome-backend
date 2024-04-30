package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final UserService userService;
    private final String endpoint = "/auth";

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(endpoint)
    public ResponseEntity<User> userLogin(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping(endpoint + "/user/recovery-password")
    public ResponseEntity<?> forgotPassword(@PathParam(value = "email") String email) {
        userService.recoveryPassword(email);
        return ResponseEntity.ok("Se ha enviado un correo con las instrucciones para recuperar la contraseña");
    }

    @PostMapping(endpoint + "/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDTO userDTO) {
        userService.changePassword(userDTO);
        return ResponseEntity.ok("Se ha cambiado la contraseña exitosamente");
    }

}
