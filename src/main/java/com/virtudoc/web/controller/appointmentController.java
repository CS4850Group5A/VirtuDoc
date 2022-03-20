package com.virtudoc.web.controller;


import com.lowagie.text.DocumentException;
import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.service.AppointmentServiceCreation;
import com.virtudoc.web.service.FileService;
import com.virtudoc.web.service.PDFGeneratorService;
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
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/appointment")
public class appointmentController {
	@Autowired
	private AppointmentServiceCreation appointmentService;

	@Autowired
	private PDFGeneratorService pdfGeneratorService;

	@Autowired
	private FileService fileService;

	//@RequestMapping(value = "/", method = RequestMethod.GET)
	@GetMapping("/show")
	public String getAppointment(Model model) {
		Appointment appointment = new Appointment();

		List<Appointment> apts = appointmentService.getAppointment();
		model.addAttribute("appointments", apts);
		model.addAttribute("appointment", new Appointment());
		return "appointment";
	}

	//@RequestMapping(value = "/", method = RequestMethod.POST)
	@PostMapping("/new")
	public String createAppointment(@ModelAttribute("appointment") Appointment appointment) throws Exception {
		Appointment apt = appointmentService.createAppointment(appointment);

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("appointment", apt);
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream)pdfGeneratorService.generatePDF(pdfModel, "pdfTest/pdfTestHTML.html");
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		fileService.CreateFile(inputStream);
		return "redirect:/appointment/show";
	}
}

