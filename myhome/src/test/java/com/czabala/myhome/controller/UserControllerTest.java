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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllUsersReturnsAllUsers() {
        Set<User> users = new HashSet<>();
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<Set<User>> response = userController.findAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void findUserByIdReturnsUser() {
        User user = new User();
        when(userService.findById(1L)).thenReturn(user);

        ResponseEntity<User> response = userController.findUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void findUserByEmailReturnsUser() {
        User user = new User();
        when(userService.findByEmail("test@test.com")).thenReturn(user);

        ResponseEntity<User> response = userController.findUserByEmail("test@test.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void findUserByRoleReturnsUsers() {
        Set<User> users = new HashSet<>();
        when(userService.findByRole("admin")).thenReturn(users);

        ResponseEntity<Set<User>> response = userController.findUserByRole("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void findUserByTokenReturnsUser() {
        User user = new User();
        when(userService.findByToken("token")).thenReturn(user);

        ResponseEntity<User> response = userController.findUserByToken("token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void createUserReturnsCreatedUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userService.add(userDTO)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void updateUserReturnsUpdatedUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userService.update(userDTO)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void deleteUserReturnsSuccessMessage() {
        ResponseEntity<String> response = userController.deleteUser(1L);
        String expectedResponse = "{\"message\": \"Usuario eliminado\"}";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}