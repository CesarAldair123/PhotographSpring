package com.example.Photograph.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    public void sendVerificationEmail(String username, String to, String token){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setSubject("Photograph Verification Email");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(verificationEmailContent(username, token), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Cannot send email to " + to);
        }
    }

    private String verificationEmailContent(String username, String token){
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("link", "localhost:8080/api/auth/verify/" + token);

        return templateEngine.process("VerificationEmail", context);
    }

}
