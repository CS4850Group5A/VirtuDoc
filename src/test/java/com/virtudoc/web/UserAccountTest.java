package com.virtudoc.web;

import com.virtudoc.web.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserAccountTest {
    private static Validator validator;

    @BeforeEach
    public void initializeValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void createValidUser() {
        UserAccount ua = new UserAccount("testuser", "testpassword", "PATIENT");
        Set<ConstraintViolation<UserAccount>> constraintViolations = validator.validate(ua);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void usernameBlank() {
        UserAccount ua = new UserAccount("", "testpassword", "PATIENT");
        Set<ConstraintViolation<UserAccount>> constraintViolations = validator.validate(ua);
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void usernameLong() {
        String username = StringUtils.repeat("a", 33);
        UserAccount ua = new UserAccount(username, "testpassword", "PATIENT");
        Set<ConstraintViolation<UserAccount>> constraintViolations = validator.validate(ua);
        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 32", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordBlank() {
        UserAccount ua = new UserAccount("testuser", "", "PATIENT");
        Set<ConstraintViolation<UserAccount>> constraintViolations = validator.validate(ua);
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void userCanHaveNoRole() {
        UserAccount ua = new UserAccount("testuser", "testpassword", "PATIENT");
        Set<ConstraintViolation<UserAccount>> constraintViolations = validator.validate(ua);
        assertEquals(0, constraintViolations.size());
    }
}
