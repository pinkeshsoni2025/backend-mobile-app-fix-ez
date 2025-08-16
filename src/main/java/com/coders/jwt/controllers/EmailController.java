package com.coders.jwt.controllers;

import com.coders.jwt.resource.EmailMessage;
import com.coders.jwt.service.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class EmailController {
@Autowired
    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity sendEmail(
            @RequestBody EmailMessage emailMessage
    ) {
        // send normal mail using subject and body
        this.emailSenderService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getMessage());

        // send mail using html template and image logo
        //this.emailSenderService.sendTestMail();
        
        
        return ResponseEntity.ok("Successfully sent email.");
    }

}
            