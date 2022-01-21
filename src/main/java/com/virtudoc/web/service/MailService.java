package com.virtudoc.web.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {
    public void SendEmail(String recipient) throws Exception { // TODO: Replace with mail DTO object
        Email from = new Email("test@examle.com");
        Email to = new Email("test2@example.com");
        String subject = "test subject";
        Content content = new Content("text/plain", "test content");
        Mail mail = new Mail(from, subject, to, content);

        // TODO: Use empty var instead of mock value?
        if (System.getenv("SENDGRID_API_KEY").equals("MOCK")) { // TODO: Use Spring config file instead of env var?
            // TODO: Print message contents to the screen.
        } else {
            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY")); // TODO: Don't re-initialize this object every time?
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                if (response.getStatusCode() != 200) {
                    throw new Exception("Sendgrid API returned non-OK status code.");
                }
            } catch (IOException e) {
                throw new Exception("Unable to communicate with Sendgrid API.", e);
            }
        }
        // TODO: Write unit tests
    }
}
