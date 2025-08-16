package com.coders.jwt.service;

import java.util.Map;

public interface EmailSenderService {

    void sendEmail(String to, String subject, String message);


    void sendTestMail();


    String createDynamicHtmlViewContent(String template, Map<String, Object> details);

}
