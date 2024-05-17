package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import com.czabala.myhome.util.mapper.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController is a REST controller that handles authentication related requests.
 * It provides endpoints for user login, user registration, password recovery, password change, user confirmation, and resending confirmation.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    /**
     * Constructs a new AuthController with the specified UserService.
     *
     * @param userService the UserService to be used by the AuthController
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles a POST request for user login.
     *
     * @param userDTO the UserDTO containing the user's login information
     * @return a ResponseEntity containing the logged in User
     */
    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", user.getToken().substring(7));
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    /**
     * Handles a POST request for user registration.
     *
     * @param userDTO the UserDTO containing the new user's information
     * @return a ResponseEntity containing the created User
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        return JsonObject.jsonMsgResponse(201, "Se ha enviado un correo de confirmación");
    }

    /**
     * Handles a GET request for password recovery.
     *
     * @param email the email of the user who forgot their password
     * @return a ResponseEntity containing a message indicating that password recovery instructions have been sent
     */
    @GetMapping("/recovery-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        userService.recoveryPassword(email);
        return JsonObject.jsonMsgResponse(200, "Se ha enviado un correo con las instrucciones para recuperar la contraseña");
    }

    /**
     * Handles a PUT request to change a user's password.
     *
     * @param email the email of the user changing their password
     * @return a ResponseEntity containing a message indicating that the password has been changed
     */
    @GetMapping("/resend-confirmation/{email}")
    public ResponseEntity<String> resendConfirmation(@PathVariable String email) {
        userService.resendConfirmation(email);
        return JsonObject.jsonMsgResponse(200, "Se ha reenviado el correo de confirmación");
    }


    /**
     * Handles a PUT request to confirm a user's account.
     *
     * @param token the confirmation token. This is a request parameter that is part of the URL.
     * @return a ResponseEntity containing a message indicating that the user has been confirmed. The status code of the ResponseEntity is 200.
     */
    @PutMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestParam String token) {
        userService.processConfirmationToken(token);
        return JsonObject.jsonMsgResponse(200, "Usuario confirmado con exito");
    }

    /**
     * Handles a PUT request to change a user's password.
     *
     * @param userDTO the UserDTO object that contains the email and new password. This object is part of the request body.
     * @return a ResponseEntity containing a message indicating that the password has been changed. The status code of the ResponseEntity is 200.
     */
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserDTO userDTO) {
        userService.changePassword(userDTO);
        return JsonObject.jsonMsgResponse(200, "Se ha cambiado la contraseña con exito");
    }
}