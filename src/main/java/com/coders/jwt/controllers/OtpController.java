package com.coders.jwt.controllers;

import java.nio.file.AccessDeniedException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.jwt.models.OtpRequest;
import com.coders.jwt.models.OtpVerificationRequest;
import com.coders.jwt.repository.UserRepository;
import com.coders.jwt.service.OtpService;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;
    
    @Autowired
	UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest request) throws AccessDeniedException {
        try {		
        	String email =request.getEmail();
        	if (!(userRepository.existsByEmail(email))) {
    			//return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    			throw new AccessDeniedException("This email is not associated with any EasyFix account.");
        	}
            otpService.sendOtp(email);
            return ResponseEntity.ok(email);
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Error sending OTP");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        boolean valid = otpService.verifyOtp(request.getEmail(), request.getOtp());
        return valid ? ResponseEntity.ok("OTP verified") : ResponseEntity.badRequest().body("Invalid or expired OTP");
    }
}
