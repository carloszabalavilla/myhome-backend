package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dto.AuthenthicationRequest;
import com.czabala.myhome.domain.model.dto.AuthenthicationResponse;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.AuthenticationService;
import com.czabala.myhome.service.UserService;
import com.czabala.myhome.util.mapper.JsonObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController is a REST controller that handles authentication related requests.
 * It provides endpoints for user login, user registration, password recovery, password change, user confirmation, and resending confirmation.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Handles a POST request for user login.
     *
     * @param authenthicationRequest the UserDTO containing the user's login information
     * @return a ResponseEntity containing the jwt
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenthicationResponse> login(@RequestBody @Valid AuthenthicationRequest authenthicationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenthicationRequest));
    }

    /**
     * Handles a POST request for user registration.
     *
     * @param userDTO the UserDTO containing the new user's information
     * @return a ResponseEntity containing the created User
     */
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO userDTO) {
        userService.add(userDTO);
        return JsonObject.jsonMsgResponse(201, "Se ha enviado un correo de confirmación");
    }

    /**
     * Handles a GET request for password recovery.
     *
     * @param email the email of the user who forgot their password
     * @return a ResponseEntity containing a message indicating that password recovery instructions have been sent
     */
    @GetMapping("/recovery-password/start/{email}")
    public ResponseEntity<String> startRecovery(@PathVariable String email) {
        userService.startChangePassword(email);
        return JsonObject.jsonMsgResponse(200, "Se ha enviado un correo con las instrucciones para recuperar la contraseña");
    }

    /**
     * Handles a GET request to check if a password recovery token is valid.
     *
     * @param jwt the password recovery token
     * @return a ResponseEntity containing the user data if the token is valid
     */
    @GetMapping("/recovery-password/let")
    public ResponseEntity<UserDTO> checkRecovery(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(userService.letChangePassword(jwt));
    }

    /**
     * Handles a POST request to change a user's password using a password recovery token.
     *
     * @param jwt  the password recovery token
     * @param auth the new password
     * @return a ResponseEntity containing the updated user data
     */
    @PostMapping("/recovery-password/change")
    public ResponseEntity<UserDTO> changePassword(@RequestHeader("Authorization") String jwt, @RequestBody @Valid AuthenthicationRequest auth) {
        return ResponseEntity.ok(userService.changePassword(jwt, auth));
    }

    /**
     * Handles a GET request to resend a confirmation email to a user.
     *
     * @param email the email of the user
     * @return a ResponseEntity containing a message indicating that the confirmation email has been resent
     */
    @GetMapping("/resend-confirmation/{email}")
    public ResponseEntity<String> resendConfirmation(@PathVariable String email) {
        userService.resendConfirmation(email);
        return JsonObject.jsonMsgResponse(200, "Se ha reenviado el correo de confirmación");
    }

    /**
     * Handles a PUT request to confirm a user's account.
     *
     * @param jwt the confirmation token. This is a request parameter that is part of the URL.
     * @return a ResponseEntity containing a message indicating that the user has been confirmed. The status code of the ResponseEntity is 200.
     */
    @PutMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestHeader("Authorization") String jwt) {
        userService.confirmEmail(jwt);
        return JsonObject.jsonMsgResponse(200, "Usuario confirmado con exito");
    }
}