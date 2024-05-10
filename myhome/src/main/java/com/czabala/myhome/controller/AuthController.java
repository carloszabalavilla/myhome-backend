package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    //private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    /*
    public record LoginRequest(String email, String password) {
    }
     */

    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody UserDTO userDTO) {

        /*
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        authenticationResponse.setAuthenticated(true);
         */
        User user = userService.login(userDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", user.getToken().substring(7));
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<?> forgotPassword(@PathParam(value = "email") String email) {
        userService.recoveryPassword(email);
        return ResponseEntity.ok("Se ha enviado un correo con las instrucciones para recuperar la contraseña");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDTO userDTO) {
        userService.changePassword(userDTO);
        return ResponseEntity.ok("Se ha cambiado la contraseña exitosamente");
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmUser(@PathParam("token") String token) {
        userService.processConfirmationToken(token);
        return ResponseEntity.ok("Usuario confirmado exitosamente");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.add(userDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<?> resendConfirmation(@PathParam("email") String email) {
        userService.resendConfirmation(email);
        return ResponseEntity.ok("Se ha reenviado el correo de confirmación");
    }
}
