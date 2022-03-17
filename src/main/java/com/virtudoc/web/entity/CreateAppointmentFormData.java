package com.virtudoc.web.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAppointmentFormData {
    @NotNull
    @Size(min = 1,max = 400)
    private String patientName;

    @NotNull
    @Size(min = 1,max = 400)
    private String symptoms;

    @NotNull
    @Size(min = 1,max = 400)
    private String doctorName;

    @NotNull
    @Size(min = 1,max = 400)
    private String location;

    @NotNull
    @Size(min = 1,max = 400)
    private String email;

    @NotNull
    @Size(min = 1,max = 400)
    private String date;

    @NotNull
    @Size(min = 1,max = 400)
    private String time;

    @NotNull
    @Size(min = 1,max = 400)
    private String reasonForVisit;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getPatientName() {
        return patientName;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public String getLocation() {
        return location;
    }
    public String getEmail() {
        return email;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public AppointmentCreationParameters toParameters() {
        return new AppointmentCreationParameters(patientName,symptoms,doctorName,
                location,email,date,time,reasonForVisit);
    }
}
