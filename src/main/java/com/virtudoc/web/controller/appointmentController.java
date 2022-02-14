package com.virtudoc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class appointmentController {

	
	@GetMapping("/appointment")
	String getAppointment() {
		return "appointment";
	}
	@GetMapping("/submit")
	String getSubmit() {
		return "AppointmentCreation";
	}
}