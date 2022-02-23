package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class CurrentAppointmentsController {

    @Autowired
    private AppointmentService service;

    @GetMapping("/notifications")
    String getNotifications(Model model) {
        //Manually add 3 appointments
//        Appointment apt1 = new Appointment();
//        apt1.setEmail("test@test.com");
//        apt1.setPatientName("John Smith");
//        apt1.setSymptoms("Covid");
//        apt1.setDoctorName("Dr. Mary Jane");
//        apt1.setLocation("Kennesaw Side Building");
//        apt1.setDate("2PM Feb 3");
//        apt1.setReasonForVisit("Covid-19 Test");
//
//        Appointment apt2 = new Appointment();
//        apt2.setEmail("test@test.com");
//        apt2.setPatientName("Jane Smith");
//        apt2.setSymptoms("Covid");
//        apt2.setDoctorName("Dr. Phil Johnson");
//        apt2.setLocation("Kennesaw Main Building");
//        apt2.setDate("3PM Feb 4");
//        apt2.setReasonForVisit("Covid-19 Test");
//
//        Appointment apt3 = new Appointment();
//        apt3.setEmail("test@test.com");
//        apt3.setPatientName("John Smith");
//        apt3.setSymptoms("Covid");
//        apt3.setDoctorName("Dr. Mary Jane");
//        apt3.setLocation("Marietta Main Building");
//        apt3.setDate("1PM Feb 7");
//        apt3.setReasonForVisit("Annual Exam");
//
//        model.addAttribute("appointments", Arrays.asList(
//                apt1, apt2, apt3
//        ));

        //Pull appointments from DB
        List<Appointment> allAppointments = service.listAll();
        model.addAttribute("appointments", allAppointments);

        return "current_appointments";
    }

    //Delete by id
    @GetMapping("/notifications/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        service.delete(id);
        return "redirect:/notifications";
    }
}