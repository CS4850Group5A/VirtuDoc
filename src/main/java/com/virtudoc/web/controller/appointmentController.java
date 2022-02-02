package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class appointmentController {

	
	@GetMapping("/appointments")
	String getAppointment() {
		return "appointments";
	}
	@GetMapping("/submit")
	String getSubmit() {
		return "confirmation";
	}
}