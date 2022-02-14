package com.virtudoc.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientRecordsController {
    @GetMapping("/patient_records")
    public String getPatientRecordsPage(){
        return "patient_records"
    }
}

