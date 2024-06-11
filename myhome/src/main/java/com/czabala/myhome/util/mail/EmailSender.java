package com.czabala.myhome.util.mail;

import com.czabala.myhome.service.JwtService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private final JavaMailSender emailSender;
    private final String urlBase = "http://localhost:3000";
    private final JwtService jwtService;

    public EmailSender(JavaMailSender emailSender, JwtService jwtService) {
        this.emailSender = emailSender;
        this.jwtService = jwtService;
    }

    public void sendConfirmationMessage(String email, String token) {
        String subject = "Confirmación de correo electrónico";
        String confirmationUrl = urlBase + "/auth/confirm?token=" + jwtService.encodeToken(token);
        String text = "Por favor, haz clic en el siguiente enlace para confirmar tu dirección de correo electrónico: \n\n"
                + confirmationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendChangePasswordMessage(String email, String token) {
        String subject = "Cambio de contraseña";
        String changePasswordUrl = urlBase + "/auth/change-password?token=" + jwtService.encodeToken(token);

        String text = "Por favor, haz clic en el siguiente enlace para cambiar tu contraseña: \n\n"
                + changePasswordUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}