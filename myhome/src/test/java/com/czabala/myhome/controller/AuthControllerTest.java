package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void userLoginReturnsUserWithToken() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        user.setToken("Bearer token");
        when(userService.login(userDTO)).thenReturn(user);

        ResponseEntity<User> response = authController.userLogin(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals("token", response.getHeaders().get("Authorization").get(0));
    }

    @Test
    public void createUserReturnsCreatedUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userService.add(userDTO)).thenReturn(user);

        ResponseEntity<?> response = authController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void forgotPasswordReturnsSuccessMessage() {
        String email = "test@test.com";
        String expectedResponse = "{\"message\": \"Se ha enviado un correo con las instrucciones para recuperar la contraseña\"}";

        ResponseEntity<?> response = authController.forgotPassword(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void changePasswordReturnsSuccessMessage() {
        String email = "test@test.com";
        String password = "newPassword";
        String expectedResponse = "{\"message\": \"Se ha cambiado la contraseña con exito\"}";
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(password);
        ResponseEntity<?> response = authController.changePassword(email, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void confirmUserReturnsSuccessMessage() {
        String token = "token";
        String expectedResponse = "{\"message\": \"Usuario confirmado con exito\"}";

        ResponseEntity<?> response = authController.confirmUser(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void resendConfirmationReturnsSuccessMessage() {
        String email = "test@test.com";
        String expectedResponse = "{\"message\": \"Se ha reenviado el correo de confirmación\"}";

        ResponseEntity<?> response = authController.resendConfirmation(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}