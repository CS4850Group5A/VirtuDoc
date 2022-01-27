package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class videoPageNavController {
    @GetMapping("/video")
    public String getVideoPage(){
        return "video";
    }
}
