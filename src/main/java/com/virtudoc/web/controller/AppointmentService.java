package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Appointment> listCustomerAppointments(String patientName) {
        return repo.listCustomerAppointments(patientName);
    }

    //List doctor appointments, performs query using current user's name
    public List<Appointment> listDoctorAppointments(String doctorName) {
        return repo.listDoctorAppointments(doctorName);
    }

    //List admin appointments, performs query where approved = false
    public List<Appointment> listAdminAppointments() {
        return repo.listAdminAppointments();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }


}
