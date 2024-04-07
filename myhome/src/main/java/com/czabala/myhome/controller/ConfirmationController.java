package com.czabala.myhome.controller;

import com.czabala.myhome.service.database.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmationController {

    private final UserService userService;

    public ConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirmEmail")
    public String confirmEmail(@RequestParam("token") String token) {
        try {
            userService.processConfirmationToken(token);
            return "redirect:/confirmacion-exitosa"; //TODO Redireccionar a una página de confirmación exitosa
        } catch (Exception e) {
            return "redirect:/confirmacion-fallida"; //TODO Redireccionar a una página de confirmación fallida
        }
    }
}
