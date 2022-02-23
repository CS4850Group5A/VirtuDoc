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

    public List<Appointment> listAll() {
        return (List<Appointment>) repo.findAll();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
