package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.enums.EmailType;
import com.campusconnect.CampusConnect.util.TemplateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(EmailType emailType, String[] to, String... placeholders) {
        try {
            if (to == null || to.length == 0) {
                log.warn("No recipient provided for email type: {}", emailType);
                return;
            }

            String subject = TemplateProvider.getSubject(emailType);
            String body = TemplateProvider.getBody(emailType, placeholders);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

            log.debug("Email sent successfully to: {} with type: {}", Arrays.toString(to), emailType);
        } catch (Exception e) {
            log.error("Exception while sending email to {}: {}", Arrays.toString(to), e.getMessage(), e);
        }
    }
}
