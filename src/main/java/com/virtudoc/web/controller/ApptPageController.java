package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApptPageController {
    @GetMapping("/ApptPage")
    public String getApptPage(){
        return "ApptPage";
    }
}
