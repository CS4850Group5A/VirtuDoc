package com.virtudoc.web.controller;


import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@SessionAttributes("user")
public class UsersChatController {
    @Autowired
    private AppointmentService service;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthenticationService aaa;

    @GetMapping("/fetchAllUsers")
    public Set<Appointment> fetchAll() {
        //Pull appointments from DB
        List<Appointment> allAppointments;
        UserAccount a = aaa.GetCurrentUser(request);

        if (a.getRole().equals("patient")) {
            allAppointments = service.listCustomerAppointments(a.getUsername());
        }
        else if (a.getRole().equals("doctor")) {
            allAppointments = service.listDoctorAppointments(a.getUsername());
        }
        else {
            allAppointments = service.listAll();
        }
        Appointment apt1 = new Appointment();
        apt1.setEmail("test@test.com");
        apt1.setPatientName("mockuser");
        apt1.setSymptoms("Covid");
        apt1.setDoctorName("Doctor Smith");
        apt1.setLocation("Kennesaw Side Building");
        apt1.setDate("2PM Feb 3");
        apt1.setReasonForVisit("Covid-19 Test");
        allAppointments.add(apt1);
        Set<Appointment> targetSet = new HashSet<>(allAppointments);
        return targetSet;
    }
}