package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class NotificationsController {

    @GetMapping("/notifications")
    String getNotifications(Model model) {
        model.addAttribute("notifications", Arrays.asList(
                new Notification("Patient 1", "Doctor 1", "Main Building", "Covid-19 Test", "February 22nd, 1PM"),
                new Notification("Patient 2", "Doctor 1", "Side Building", "Annual Exam", "February 24nd, 1PM"),
                new Notification("Patient 3", "Doctor 2", "Main Building", "Fever Symptoms", "February 26nd, 1PM")
        ));
        return "notifications";
    }
}
