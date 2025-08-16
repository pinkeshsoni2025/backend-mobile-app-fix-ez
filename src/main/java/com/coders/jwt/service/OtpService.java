package com.coders.jwt.service;

import java.nio.file.Files;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String PREFIX = "otp:";

    public void sendOtp(String email) throws MessagingException, IOException {
        String otp = String.format("%06d", new Random().nextInt(999999));
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(PREFIX + email, otp, 2, TimeUnit.MINUTES);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Your FixEz OTP Code");

        // Load HTML template and replace OTP placeholder
        String html = "<html><body style='font-family: Arial, sans-serif;'><div style='max-width:600px;margin:auto;padding:20px;background:#fff;border-radius:10px;'><div style='text-align:center;margin-bottom:20px;'><img src='cid:fixez-logo' alt='FixEz Logo' width='150'></div> <h2>Hello!</h2><p>Your OTP for FixEz is:</p>"
        		+"<h2 style=\"border: 2px solid #000000;margin: 0 auto;width: max-content;padding: 0 5px;color: #2384d7;border-radius: 4px;letter-spacing: 4px;font-size:25px;\">{{OTP_CODE}}</h2>"
        		+"<p>Thank you for choosing <strong>FixEz</strong>. Use this OTP to verify your account on FixEz.</p>"
        		+ "<p>This OTP is valid for 2 minutes. If you didn't request it, please ignore.</p>"
        		+ "<p style=\"font-size:15px;\"><strong>Regards,</strong><br />Team FixEz</p>\n"
        		+ "<hr style=\"border:none; border-top:2px solid #000000\" />"
        		+ "<p><strong>FixEz</strong> – Fixing everyday problems made simple!</p></div></body></html>";

        html = html.replace("{{OTP_CODE}}", otp);
        helper.setText(html, true);

        // Inline image from resources
        ClassPathResource logo = new ClassPathResource("img/logo-FixEz.png");
        helper.addInline("fixez-logo", logo);

        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedOtp = ops.get(PREFIX + email);
        return otp.equals(storedOtp);
    }
}
