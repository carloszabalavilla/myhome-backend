package com.czabala.myhome.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final String urlBase = "http://localhost:3000";

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);
    }

    @Override
    public void sendMessageUsingTemplate(String to, String subject, String... templateModel) {

    }

    @Override
    public void sendConfirmationMessage(String to, String token) {
        String subject = "Confirmación de correo electrónico";
        String confirmationUrl = urlBase + "/auth/confirm?token=" + token;
        String text = "Por favor, haz clic en el siguiente enlace para confirmar tu dirección de correo electrónico: \n\n"
                + confirmationUrl;
//        String button = "<center>\n" +
//                " <table align=\"center\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
//                "   <tr>\n" +
//                "     <td align=\"center\" style=\"padding: 10px;\">\n" +
//                "       <table border=\"0\" class=\"mobile-button\" cellspacing=\"0\" cellpadding=\"0\">\n" +
//                "         <tr>\n" +
//                "           <td align=\"center\" bgcolor=\"#00ffd9\" style=\"background-color: #00ffd9; margin: auto; max-width: 600px; -webkit-border-radius: 13px; -moz-border-radius: 13px; border-radius: 13px; padding: 15px 20px; \" width=\"100%\">\n" +
//                "           <!--[if mso]>&nbsp;<![endif]-->\n" +
//                "               \"<a href=\\\"" + confirmationUrl + "\\\" target=\\\"_blank\\\" style=\\\"font-size: 14px; font-family: Verdana, Geneva, sans-serif; color: #000000; font-weight:normal; text-align:center; background-color: #00ffd9; text-decoration: none; border: none; -webkit-border-radius: 13px; -moz-border-radius: 13px; border-radius: 13px; display: inline-block;\\\">Confirmar cuenta " +
//                "           <!--[if mso]>&nbsp;<![endif]-->\n" +
//                "           </td>\n" +
//                "         </tr>\n" +
//                "       </table>\n" +
//                "     </td>\n" +
//                "   </tr>\n" +
//                " </table>\n" +
//                "</center>";
//        String text = "Por favor, haz clic en el siguiente enlace para confirmar tu dirección de correo electrónico: \n\n"
//                + button;

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        emailSender.send(message);
    }

    @Override
    public void sendRecoveryMessage(String email, String token) {
        String subject = "Recuperación de contraseña";
        String recoveryUrl = urlBase + "/auth/reset-password?token=" + token;

        String text = "Por favor, haz clic en el siguiente enlace para recuperar tu contraseña: \n\n"
                + recoveryUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}