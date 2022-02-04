package com.virtudoc.web.controller;

import com.virtudoc.web.dto.NewUserDTO;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.service.AuthenticationService;
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

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("user", new NewUserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute NewUserDTO userDTO, Errors errors) {
        try {
            authenticationService.RegisterNewAccount(new UserAccount(userDTO));
        } catch (Exception e) {
            return "redirect:/register";
        }
        return "redirect:/login";
    }

}
