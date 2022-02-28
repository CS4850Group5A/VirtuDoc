package com.virtudoc.web.runner;

import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.repository.UserAccountRepository;
import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class MockUserSeeder implements CommandLineRunner {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userAccountRepository.count() == 0) { // Do not run migrations unless this is an empty DB.
            // Used with the GetCurrentUser() method in the AuthenticationService. DO NOT REMOVE.
            authenticationService.RegisterNewAccount(new UserAccount("mockuser", "mockuser", "PATIENT"));
        }
    }
}
