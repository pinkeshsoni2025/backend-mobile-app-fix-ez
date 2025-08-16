package com.coders.jwt.service;


import jakarta.mail.Part;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.mail.Multipart;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    private static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessage";
    private static final String MAIL_TEMPLATE_PREFIX = "/templates/";
    private static final String MAIL_TEMPLATE_SUFFIX = ".html";
    private static final String UTF_8 = "UTF-8";

    @Value("${spring.mail.username}")
    private String email;

    @Value("${to.mail.address}")
    private String toEmailAddress;

    private static TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(htmlTemplateResolver());
        springTemplateEngine.setTemplateEngineMessageSource(emailMessageSource());

        return springTemplateEngine;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();

        resourceBundleMessageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);

        return  resourceBundleMessageSource;
    }

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(email);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        this.mailSender.send(mailMessage);

    }

    @Override
    public void sendTestMail() {
        try {

            jakarta.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();


            // create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Just discovered that Jakarta Mail is fun and easy to use!");

            // create a second MimeBodyPart to hold the attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String filename = "/path/to/your/file.txt"; // Specify the file path
            attachmentPart.attachFile(new File(filename));

            // create a Multipart object to combine the message body and the attachment
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);  // Add message body
            multipart.addBodyPart(attachmentPart);   // Add attachment

            mimeMessage.setContent(multipart,"text/html; charset=UTF-8");

            mailSender.send(mimeMessage);
            System.out.println("Mail sent successfully");
        } catch (Exception exception) {
           // log.error("Error Occurs: message - {} ", exception.getMessage());
        	System.out.println("Error Occurs: message - {} "+ exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    public String createDynamicHtmlViewContent(String template, Map<String, Object> details) {
        final Context context = new Context();

        context.setVariables(details);

        return templateEngine.process(template, context);
    }
}
