package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class messagePageNavController {
    @GetMapping("/message")
    public String getMessagePage(){
        return "message";
    }
}
