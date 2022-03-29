package com.virtudoc.web.service;

import com.virtudoc.web.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Set;

/**
 * Abstraction layer for sending emails to users. Works independent of underlying SMTP/API implementation. Class is a
 * normal service object that can be injected into any controller or service.
 *
 * @author ARMmaster17
 */
@Service
public class MailService {
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Validator validator;

    /**
     * Sends an email, using information from the given EmailDTO object. Automatically performs validation.
     * @param email Data Transfer Object with information needed to construct email message.
     * @see EmailDTO
     * @throws Exception exception
     */
    
    public void SendEmail(final EmailDTO email) throws Exception {
        validateEmailDTO(email);
        emailSender.send(convertEmailDTO(email));
    }

    private MimeMessage convertEmailDTO(final EmailDTO email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom("no-reply@virtudoc.herokuapp.com");
        helper.setTo(email.getRecipient());
        helper.setSubject(email.getSubject());
        helper.setText(this.getTemplateHTML(email), true);
        return message;
    }

    private String getTemplateHTML(final EmailDTO email) {
        Context templateEngineContext = new Context(Locale.getDefault(), email.getTemplateVariables());
        return this.templateEngine.process(email.getTemplatePath(), templateEngineContext);
    }

    private void validateEmailDTO(final EmailDTO email) throws Exception {
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(email);
        if (constraintViolations.size() > 0) {
            throw new Exception("invalid EmailDTO object");
        }
    }
}
