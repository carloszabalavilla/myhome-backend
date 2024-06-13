package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.AuthenticationRequest;
import com.czabala.myhome.domain.model.dto.AuthenticationResponse;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.AuthenticationService;
import com.czabala.myhome.service.UserService;
import com.czabala.myhome.util.mapper.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginReturnsUserWithTokenWhenCredentialsAreValid() {
        AuthenticationRequest request = new AuthenticationRequest("test@test.com", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token", new User().toDTO());
        when(authenticationService.login(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createUserReturnsCreatedUserWhenUserDoesNotExist() {
        UserDTO userDTO = new UserDTO();
        when(userService.add(userDTO)).thenReturn(userDTO);

        ResponseEntity<String> response = authController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void startRecoveryReturnsSuccessMessageWhenEmailExists() {
        String email = "test@test.com";
        doNothing().when(userService).startChangePassword(email);

        ResponseEntity<String> response = authController.startRecovery(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void checkRecoveryReturnsUserDataWhenTokenIsValid() {
        String jwt = "token";
       UserDTO userDTO = new UserDTO();
        when(userService.letChangePassword(jwt)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = authController.checkRecovery(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void changePasswordReturnsUpdatedUserDataWhenTokenIsValid() {
        String jwt = "token";
        AuthenticationRequest auth = new AuthenticationRequest("test@test.com", "newPassword");
        UserDTO userDTO = new UserDTO();
        when(userService.changePassword(jwt, auth)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = authController.changePassword(jwt, auth);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void resendConfirmationReturnsSuccessMessageWhenEmailExists() {
        String email = "test@test.com";
        doNothing().when(userService).resendConfirmation(email);

        ResponseEntity<String> response = authController.resendConfirmation(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void confirmUserReturnsSuccessMessageWhenTokenIsValid() {
        Map<String, Object> jwt = Map.of("token", "token");
        when(userService.confirmEmail(jwt.get("token").toString())).thenReturn(new UserDTO());

        ResponseEntity<String> response = authController.confirmUser(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}