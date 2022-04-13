package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repo;

    //List all appointments - for admins
    public List<Appointment> listAll() {
        return repo.findAll();
    }

   // List customer appointments, performs query using current user's name
    public List<Appointment> listCustomerAppointments(String patientName, Date currDate) {
        return repo.listCustomerAppointments(patientName, currDate);
    }

    //List doctor appointments, performs query using current user's name
    public List<Appointment> listDoctorAppointments(String doctorName, Date currDate) {
        return repo.listDoctorAppointments(doctorName, currDate);
    }

    //List admin appointments, performs query where approved = false
    public List<Appointment> listAdminAppointments(Date currDate) {
        return repo.listAdminAppointments(currDate);
    }

    //List all doctor appointments, for doctor calendar
    public List<Appointment> listAllDoctorAppointments(String doctorName) {
        return repo.listAllDoctorAppointments(doctorName);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public void approve(int id) {
        Appointment apt = repo.findById(id).orElse(null);
        apt.setApproved(true);
        repo.save(apt);
    }
}
