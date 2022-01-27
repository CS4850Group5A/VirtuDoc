package com.virtudoc.web.controller;
import java.time.LocalDateTime;

public class Notification {
    private String patientName;
    private String doctorName;
    private String location;
    private String reasonForVisit;
//    todo: convert to Date object
    private String date;


    public Notification() {
    }

    public Notification(String patientName, String doctorName, String location, String reasonForVisit, String date) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.location = location;
        this.reasonForVisit = reasonForVisit;
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
