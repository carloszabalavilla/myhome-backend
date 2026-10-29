package com.czabala.myhome.util.mail;

import com.czabala.myhome.service.JwtService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Component
public class EmailSender {

    private final JavaMailSender emailSender;
    @Value("${myhome.frontend-url:http://localhost:3000}")
    private String urlBase;
    @Value("${myhome.mail.local-directory:}")
    private String localDirectory;
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
        deliver(message);
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
        deliver(message);
    }
    private void deliver(SimpleMailMessage message) {
        if (localDirectory == null || localDirectory.isBlank()) { emailSender.send(message); return; }
        try {
            Path directory = Path.of(localDirectory);
            Files.createDirectories(directory);
            Files.writeString(directory.resolve(UUID.randomUUID() + ".txt"),
                    "To: " + String.join(", ", message.getTo()) + "\nSubject: " + message.getSubject() + "\n\n" + message.getText());
        } catch (IOException ex) { throw new UncheckedIOException("Cannot capture demo email", ex); }
    }
}
