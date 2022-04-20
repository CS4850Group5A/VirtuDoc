package com.virtudoc.web.controller;

import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AdminRecordsController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/files")
    public String listUploadedFiles(HttpServletRequest request, Model model) throws IOException {

        model.addAttribute("files", fileRepository.findAll());
        model.addAttribute("user", authenticationService.GetCurrentUser(request));

        return "admin_records";
    }

    @PostMapping("/files")
    public String returnHack() {
        return "redirect:/files";
    }
}
