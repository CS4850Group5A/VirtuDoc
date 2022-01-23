package com.virtudoc.web;

import com.virtudoc.web.dto.EmailDTO;
import com.virtudoc.web.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@EnabledIf(value = "#{'${test.mail}' == 'true'}", loadContext = true) // Only run on GHA or Compose-managed stack.
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail() throws Exception {
        EmailDTO emailDTO = new EmailDTO("test@example.com", "test subject", "mail/test");
        mailService.SendEmail(emailDTO);
    }
}
