package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.enums.EmailType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testMaleSender(){
        emailService.sendMail(EmailType.WELCOME, new String[]{"golanihimanshu@gmail.com"}, "John Doe");

        emailService.sendMail(EmailType.POST_NOTIFIER, new String[]{"golanihimanshu@gmail.com","golanihimanshu2@gmail.com"}, "John Doe", "New Job Openings in Google!");

        emailService.sendMail(EmailType.WARNING, new String[]{"golanihimanshu@gmail.com","golanihimanshu2@gmail.com"}, "John Doe");

    }
}
