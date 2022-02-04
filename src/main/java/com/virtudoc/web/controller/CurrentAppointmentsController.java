package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class CurrentAppointmentsController {

    @GetMapping("/notifications")
    String getNotifications(Model model) {
        Appointment apt1 = new Appointment();
        apt1.setEmail("test@test.com");
        apt1.setPatientName("Patient 1");
        apt1.setSymptoms("Covid");
        apt1.setDoctorName("Doctor 1");
        apt1.setLocation("Kennesaw");
        apt1.setDate("Feb 3");
        apt1.setReasonForVisit("Reason for Visit");

        Appointment apt2 = new Appointment();
        apt2.setEmail("test@test.com");
        apt2.setPatientName("Patient 1");
        apt2.setSymptoms("Covid");
        apt2.setDoctorName("Doctor 1");
        apt2.setLocation("Kennesaw");
        apt2.setDate("Feb 3");
        apt2.setReasonForVisit("Reason for Visit");

        Appointment apt3 = new Appointment();
        apt3.setEmail("test@test.com");
        apt3.setPatientName("Patient 1");
        apt3.setSymptoms("Covid");
        apt3.setDoctorName("Doctor 1");
        apt3.setLocation("Kennesaw");
        apt3.setDate("Feb 3");
        apt3.setReasonForVisit("Reason for Visit");

        model.addAttribute("notifications", Arrays.asList(
                apt1, apt2, apt3
        ));
        return "current_appointments";
    }
}
