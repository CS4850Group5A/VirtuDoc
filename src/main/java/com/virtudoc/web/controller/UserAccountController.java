package com.virtudoc.web.controller;

import com.virtudoc.web.dto.EmailDTO;
import com.virtudoc.web.dto.NewUserDTO;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.service.AuthenticationService;
import com.virtudoc.web.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAccountController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    String login() {
        return "login";
    }


    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("user", new NewUserDTO());
        return "register";
    }

    @GetMapping("/HIPAA_consent")
    String consent() {
        return "HIPAA_consent.html";
    }

    @GetMapping("/checkEmail")
    String check()
    {
        return "checkEmail";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute NewUserDTO userDTO, Errors errors) {
        try {
            authenticationService.RegisterNewAccount(new UserAccount(userDTO));
        } catch (Exception e) {
            return "redirect:/register";
        }
        EmailDTO newUserEmail = new EmailDTO(userDTO.getEmail(), "Verify your E-mail", "/mail/welcome.html");
        try {
            mailService.SendEmail(newUserEmail);
        } catch (Exception e) {
            // TODO: merge in with the main branch and log an exception here using SLF4J that an error occured with the email service.
            // also potentially delete the un-verified account.
            return "redirect:/register";
        }
        return "redirect:/checkEmail"; // TODO: change to "check your emails" page
    }

    @PostMapping("/welcome")
    public String emailVerify()
    {
        return "redirect:/HIPPA_consent.html";
    }

    @PostMapping("/HIPAA_consent")
    public String submitConsent()
    {
        return "redirect:/login.html";
    }

}
