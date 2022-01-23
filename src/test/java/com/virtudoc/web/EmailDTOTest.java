package com.virtudoc.web;

import com.virtudoc.web.dto.EmailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailDTOTest {

    private static Validator validator;

    @BeforeEach
    public void initializeValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validEmailDTOShort() {
        EmailDTO emailDTO = new EmailDTO("test@examle.com", "test subject", "mail/test");
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validEmailDTOLong() {
        Map<String, String> variableMap = new HashMap<String, String>();
        EmailDTO emailDTO = new EmailDTO("test@examle.com", "test subject", "mail/test", variableMap);
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidEmail() {
        EmailDTO emailDTO = new EmailDTO("bademail", "test subject", "mail/test");
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(1, constraintViolations.size());
        assertEquals("not a well-formed email address", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shortSubject() {
        EmailDTO emailDTO = new EmailDTO("test@example.com", "s", "mail/test");
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 998", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void longSubject() {
        String subject = StringUtils.repeat("a", 999);
        EmailDTO emailDTO = new EmailDTO("test@example.com", subject, "mail/test");
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 998", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void emptyTemplatePath() {
        EmailDTO emailDTO = new EmailDTO("test@example.com", "test subject", "");
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(1, constraintViolations.size());
        assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void nullVariableMap() {
        EmailDTO emailDTO = new EmailDTO("test@example.com", "test subject", "mail/test", null);
        Set<ConstraintViolation<EmailDTO>> constraintViolations = validator.validate(emailDTO);
        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }
}
