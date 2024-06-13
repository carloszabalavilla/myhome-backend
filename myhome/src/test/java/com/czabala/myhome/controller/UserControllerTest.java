package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.UserService;
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
        Set<UserDTO> users = new HashSet<>();
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<Set<UserDTO>> response = userController.findAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void findUserByIdReturnsUserWhenUserExists() {
        UserDTO userDTO = new UserDTO();
        when(userService.findById(1L)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.findUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void findUserByEmailReturnsUserWhenEmailExists() {
        UserDTO userDTO = new UserDTO();
        when(userService.findByEmail("test@test.com")).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.findUserByEmail("test@test.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void findUserByRoleReturnsUsersWhenRoleExists() {
        Set<UserDTO> users = new HashSet<>();
        when(userService.findByRole("admin")).thenReturn(users);

        ResponseEntity<Set<UserDTO>> response = userController.findUserByRole("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void createUserReturnsCreatedUserWhenUserDoesNotExist() {
        UserDTO userDTO = new UserDTO();
        when(userService.add(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void updateUserReturnsUpdatedUserWhenUserExists() {
        UserDTO userDTO = new UserDTO();
        when(userService.update(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void deleteUserReturnsSuccessMessageWhenUserExists() {
        ResponseEntity<String> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}