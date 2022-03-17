package com.virtudoc.web.controller;


import com.lowagie.text.DocumentException;
import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.service.AppointmentServiceCreation;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.List;

@Controller
@RequestMapping(path = "/appointment")
public class appointmentController {
	@Autowired
	private AppointmentServiceCreation appointmentService;

	//@RequestMapping(value = "/", method = RequestMethod.GET)
	@GetMapping("/show")
	public String getAppointment(Model model) {
		List<Appointment> apts = appointmentService.getAppointment();
		model.addAttribute("appointments", apts);
		model.addAttribute("appointment", new Appointment());
		return "appointment";
	}

	//@RequestMapping(value = "/", method = RequestMethod.POST)
	@PostMapping("/new")
	public String createAppointment(Model model, @ModelAttribute Appointment appointment) {
		Appointment apt = appointmentService.createAppointment(appointment);
		return "redirect:/appointment/show";
	}
}

/*
public class appointmentController {
	private final AppointmentService service;
	public appointmentController(AppointmentService service) {
		this.service = service;
	}

	@GetMapping("/new")
	public String submit(Model model) {

		model.addAttribute("appointment", new CreateAppointmentFormData());

		List<String> listSymptoms = Arrays.asList("-- Select Symptoms --", "Covid-19", "Stomach Aches/Diarrhea/Chills",
				"Backpain/Jointpain");
		model.addAttribute("listSymptoms", listSymptoms);

		List<String> listLocation = Arrays.asList( "-- Select Loation --", "Marietta", "Kennesaw",
				"Buckhead");
		model.addAttribute("listLocation", listLocation);


		List<String> listDoctor = Arrays.asList( "-- Select Doctor --","John Smith", "Peggy Sue",
				"Jane Doe");
		model.addAttribute("listDoctor", listDoctor);



		return "/appointment";
	}

	@PostMapping("/appointment")
	public String submitForm(@ModelAttribute("appointment") Appointment appointment) {

		return "/AppointmentCreation";
		}
	}
 */