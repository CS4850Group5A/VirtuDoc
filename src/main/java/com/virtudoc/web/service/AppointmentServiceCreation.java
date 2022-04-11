package com.virtudoc.web.service;

import com.virtudoc.web.dto.appointmentDTO;
import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceCreation {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentServiceCreation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAppointment() {

        return appointmentRepository.findAll();
    }

    public Appointment createAppointment(appointmentDTO appointment) {
        Appointment appointment2 = new Appointment(appointment);
        return appointmentRepository.save(appointment2);
    }
}
