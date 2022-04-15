package com.virtudoc.web.controller;


import com.lowagie.text.DocumentException;
import com.virtudoc.web.dto.appointmentDTO;
import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.service.AppointmentServiceCreation;
import com.virtudoc.web.service.AuthenticationService;
import com.virtudoc.web.service.FileService;
import com.virtudoc.web.service.PDFGeneratorService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	@Autowired
	private AuthenticationService authenticationService;

	//@RequestMapping(value = "/", method = RequestMethod.GET)
	@GetMapping("/show")
	public String getAppointment(HttpServletRequest request, Model model) {
		Appointment appointment = new Appointment();

		List<Appointment> apts = appointmentService.getAppointment();
		model.addAttribute("user", authenticationService.GetCurrentUser(request));
		model.addAttribute("appointments", apts);
		model.addAttribute("appointment", new appointmentDTO());
		return "appointment";
	}

	//@RequestMapping(value = "/", method = RequestMethod.POST)
	// Thymeleaf notification created through redirectAttributes
	@PostMapping("/new")
	public String createAppointment(@ModelAttribute("appointment") appointmentDTO appointment, BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		redirectAttributes.addFlashAttribute("message","Success");
		//redirectAttributes.addFlashAttribute("alertClass", "alert-success");


		Appointment apt = appointmentService.createAppointment(appointment);

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("appointment", apt);
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream)pdfGeneratorService.generatePDF(pdfModel, "pdfTest/pdfTestHTML.html");
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		fileService.CreateFile(inputStream);
		return "redirect:/appointment/show";
	}



}

