package com.virtudoc.web;

import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.repository.UserAccountRepository;
import com.virtudoc.web.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    private void deleteAllUsers() {
        userAccountRepository.deleteAll();
    }

    @Test
    public void canRegisterNewUser() throws Exception {
        String username = "testuser";
        UserAccount ua = new UserAccount(username, "testpassword", "PATIENT");
        authenticationService.RegisterNewAccount(ua);
        assertUserDoesExist(username);
    }

    @Test
    public void blocksInvalidUsernameOnRegistration() throws Exception {
        UserAccount ua = new UserAccount("", "testpassword", "PATIENT");
        assertThrows(Exception.class, () -> authenticationService.RegisterNewAccount(ua));
        assertEquals(0, userAccountRepository.count());
    }

    @Test
    public void blocksInvalidPasswordOnRegistration() throws Exception {
        String username = "testuser";
        UserAccount ua = new UserAccount(username, "", "PATIENT");
        assertThrows(Exception.class, () -> authenticationService.RegisterNewAccount(ua));
        assertUserDoesNotExist(username);
    }

    @Test
    public void authenticationServiceHashesPassword() throws Exception {
        String password = "testpassword";
        String username = "testuser";
        UserAccount ua = new UserAccount(username, password, "PATIENT");
        authenticationService.RegisterNewAccount(ua);
        assertUserDoesExist(username);
        ua = userAccountRepository.findByUsername(username).get(0);
        assertNotEquals(password, ua.getPassword());
    }

    private void assertUserDoesNotExist(String username) {
        assertEquals(0, userAccountRepository.findByUsername(username).size());
    }

    private void assertUserDoesExist(String username) {
        assertEquals(1, userAccountRepository.findByUsername(username).size());
    }
}
