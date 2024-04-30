package com.czabala.myhome.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;

    void sendMessageUsingTemplate(String to, String subject, String... templateModel);

    void sendConfirmationMessage(String to, String token);

    void sendRecoveryMessage(String email, String token);
}
