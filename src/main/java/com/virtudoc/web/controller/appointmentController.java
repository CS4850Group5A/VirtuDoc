package com.virtudoc.web.controller;

import com.virtudoc.web.dto.AppointmentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

@Controller
public class appointmentController {

	@GetMapping("/book")
		public String getBook() {
			return "appointment";
	}
	@PostMapping("/submit")
	public String submit (@RequestParam("FullName") String FullName,
							 @RequestParam("Email") String Email, @RequestParam("Phone")
							 String Phone, @RequestParam("Symptoms") String Symptoms,
							@RequestParam("Doctor") String Doctor, @RequestParam("Location")
							String Location, @RequestParam("AppointmentDate") String AppointmentDate,
							@RequestParam("AppointmentTime") String AppointmentTime,
							@RequestParam("Comment") String Comment, ModelMap modelMap) {

			modelMap.put("Full Name", FullName);
			modelMap.put("Email", Email);
			modelMap.put("Phone", Phone);
			modelMap.put("Symptoms", Symptoms);
			modelMap.put("Doctor", Doctor);
			modelMap.put("Location", Location);
			modelMap.put("Appointment Date", AppointmentDate);
			modelMap.put("Appointment Time", AppointmentTime);
			modelMap.put("Comment", Comment);
			return "AppointmentCreation";
	}

}
