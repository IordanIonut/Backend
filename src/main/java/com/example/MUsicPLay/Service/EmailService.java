package com.example.MUsicPLay.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.io.InputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    public void sendEmail(String name, String email, String subject, String message) throws MessagingException, IOException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo("MUsicPLay@support.com");
        messageHelper.setSubject(subject);
        messageHelper.setFrom(email);
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("subject",subject);
        context.setVariable("message", message);
        String emailContent = templateEngine.process("email-template.html", context);
        messageHelper.setText(emailContent, true);
        ClassPathResource resource = new ClassPathResource("static/images/Logo.jpg");
        byte[] imageBytes = IOUtils.toByteArray(resource.getInputStream());

        messageHelper.addInline("logo", new ByteArrayResource(imageBytes), "image/jpeg");
        emailSender.send(mimeMessage);
    }
    public void sendEmailChangePassword(String email, String code) throws MessagingException, IOException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(email);
        messageHelper.setSubject("Change password MUsicPLay");
        messageHelper.setFrom("MUsicPLay@support.com");
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("email", email);
        String emailContent = templateEngine.process("reset-template.html", context);
        messageHelper.setText(emailContent, true);
        ClassPathResource resource = new ClassPathResource("static/images/Logo.jpg");
        byte[] imageBytes = IOUtils.toByteArray(resource.getInputStream());

        messageHelper.addInline("logo", new ByteArrayResource(imageBytes), "image/jpeg");
        emailSender.send(mimeMessage);
    }
}