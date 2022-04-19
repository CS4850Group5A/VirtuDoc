package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class aboutController {

    /**
     * Render endpoint for the home page.
     * @return Testing string to confirm Tomcat serer is running.
     */
    @GetMapping("/about")
    public String about() {

        return "about";
    }
}
