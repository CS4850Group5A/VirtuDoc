package com.virtudoc.web.controller;

import com.virtudoc.web.dto.EmailDTO;
import com.virtudoc.web.dto.NewUserDTO;
import com.virtudoc.web.dto.resetPasswordDTO;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.repository.UserAccountRepository;
import com.virtudoc.web.service.AuthenticationService;
import com.virtudoc.web.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserAccountController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        // Check if we were redirected from POST:/login with an error.
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        // Check if we were redirected from GET:/logout.
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out!");
        }
        // Return the login.html template.
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        // Invalidate the user session and their cookies through the Spring Security framework.
        request.getSession().invalidate();
        // Redirect to login page.
        return "redirect:/login?logout";
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

    @GetMapping("/forgotMyPassword")
    String forgot(){ return "forgotMyPassword"; }

    @PostMapping("/forgotMyPassword")
    public String submitForgot(@RequestParam("email") String email, @RequestParam("username") String username)
    {
        EmailDTO fmpEmail = new EmailDTO(email, "Reset Password", "/mail/resetEmail.html");
        try {
            mailService.SendEmail(fmpEmail);
        } catch (Exception e){
            return "redirect:/forgotMyPassword";
        }

        return "redirect:/checkEmail";
    }

    @GetMapping("/resetEmail")
    String resetEmail(){
        return "resetEmail.html";
    }

    @GetMapping("/newPassword")
    String newPassword() { return "newPassword"; }

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
        return "redirect:/checkEmail";
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
