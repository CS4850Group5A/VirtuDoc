package com.virtudoc.web.controller;


import com.virtudoc.web.entity.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class appointmentController {

	@GetMapping("/appointment")
	public String submit(Model model) {
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);

		List<String> listSymptoms = Arrays.asList("-- Select Symptoms --", "Covid-19", "Stomach Aches/Diarrhea/Chills",
				"Backpain/Jointpain");
		model.addAttribute("listSymptoms", listSymptoms);

		List<String> listLocation = Arrays.asList( "-- Select Loation --", "Marietta", "Kennesaw",
				"Buckhead");
		model.addAttribute("listLocation", listLocation);


		List<String> listDoctor = Arrays.asList( "-- Select Doctor --","John Smith", "Peggy Sue",
				"Jane Doe");
		model.addAttribute("listDoctor", listDoctor);


		return "appointment";
	}

	@PostMapping("/appointment")
	public String submitForm(@ModelAttribute("appointment") Appointment appointment) {

		return "appointmentCreation";
	}
}
