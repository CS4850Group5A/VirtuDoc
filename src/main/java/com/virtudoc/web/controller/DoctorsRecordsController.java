package com.virtudoc.web.controller;

public class DoctorsRecordsController {
    @GetMapping("/doctors_records")
    public String getPatientRecordsPage(){
        return "doctors_records"
    }
}
