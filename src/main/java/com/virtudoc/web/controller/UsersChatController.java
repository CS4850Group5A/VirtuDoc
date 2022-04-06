package com.virtudoc.web.controller;


import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@SessionAttributes("user")
public class UsersChatController {
    @Autowired
    private AppointmentService service;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        //Pull appointments from DB
        List<Appointment> allAppointments = null;
        Set<String> ChatList = new HashSet<String>();

        //UserAccount a = new UserAccount("mockuser", "mockuser", "doctor");
        UserAccount a = authenticationService.GetCurrentUser(request);

        Date currDate = new Date();
        if (a.getRole().equalsIgnoreCase("patient")) {
            allAppointments = service.listCustomerAppointments(a.getUsername(), currDate);
            for (Appointment app : allAppointments) {
                ChatList.add(app.getDoctorName());
            }
            ChatList.add("Doctor Smith");
            ChatList.add("Not SMITH");
        } else if (a.getRole().equalsIgnoreCase("doctor")) {
            allAppointments = service.listDoctorAppointments(a.getUsername(), currDate);
            for (Appointment app : allAppointments) {
                ChatList.add(app.getPatientName());
            }
            ChatList.add("Patient John");
            ChatList.add("Not John");
        }
        if (ChatList.size() == 0) {
            ChatList.add("NO APPOINTMENTS");
        }

        return ChatList;
    }
}
