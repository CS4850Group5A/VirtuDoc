package com.virtudoc.web.controller;

import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.service.AuthenticationService;
import com.virtudoc.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminRecordsController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/admin_records")
    public String getMessagePage(HttpServletRequest request, Model model){
        model.addAttribute("user", authenticationService.GetCurrentUser(request));
        model.addAttribute("files", fileRepository.findAll());
        return "admin_records";
    }
}
