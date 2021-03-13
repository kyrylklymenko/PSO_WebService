package com.geiko.security.service;



import com.geiko.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;


@Service
public class MailService {


    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private final String USER = "user";
    private final String BASE_URL = "baseUrl";

    @Value(value = "email.from")
    private String from;

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    @Async
    public void sendActivationEmail(String to, String activationKey, User user, String baseUrl){
        log.info("Sending activation message: ");
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("activation", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        send(user.getEmail(), "psoservice.ua@gmail.com", subject, content);
        log.info("Message sanded to: ", user.getEmail());
    }

    @Async
    public void send(String to, String from, String subject, String content){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
            mailMsg.setFrom(from);
            mailMsg.setTo(to);
            mailMsg.setSubject(subject);
            mailMsg.setText(content, true);
        }catch (MessagingException n){
            log.warn("Error to send message to: ", to, n.getMessage());
        }
        javaMailSender.send(mimeMessage);
    }

}
