package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.UserDTO;
import com.czabala.myhome.service.database.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllAdminReturnsAllAdmins() {
        Set<User> admins = new HashSet<>();
        when(userService.findByRole("ADMIN")).thenReturn(admins);

        var response = adminController.findAllAdmin();

        assertEquals(admins, response.getBody());
    }

    @Test
    public void addAdminReturnsAdmin() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        userDTO.setId(1L);
        userDTO.setRole("ADMIN");
        when(userService.update(userDTO)).thenReturn(user);

        var response = adminController.addAdmin(1L);

        assertEquals(user, response);
    }

    @Test
    public void removeAdminReturnsUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        userDTO.setId(1L);
        userDTO.setRole("USER");
        when(userService.update(userDTO)).thenReturn(user);

        var response = adminController.removeAdmin(1L);

        assertEquals(user, response);
    }

    @Test
    public void deleteAdminDeletesAdmin() {
        adminController.deleteAdmin(1L);
    }
}