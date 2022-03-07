package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Render endpoint for the home page.
     * @return Testing string to confirm Tomcat serer is running.
     */
    @GetMapping("/")
    public String index() {
        return "landing.html";
    }
}
