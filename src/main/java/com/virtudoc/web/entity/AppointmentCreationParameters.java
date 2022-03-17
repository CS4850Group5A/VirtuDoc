package com.virtudoc.web.entity;

import org.springframework.util.Assert;

public class AppointmentCreationParameters {
    private final String patientName;
    private final String symptoms;
    private final String doctorName;
    private final String location;
    private final String email;
    private final String date;
    private final String time;
    private final String reasonForVisit;

    public AppointmentCreationParameters(String patientName,String symptoms,
                                         String doctorName, String location,
                                         String email,String date,
                                         String time, String reasonForVisit) {
        Assert.notNull(patientName, "patientName should not be null");
        Assert.notNull(symptoms, "symptoms should not be null");
        Assert.notNull(doctorName, "doctorName should not be null");
        Assert.notNull(location, "location should not be null");
        Assert.notNull(email, "email should not be null");
        Assert.notNull(date, "date should not be null");
        Assert.notNull(time, "time should not be null");
        Assert.notNull(reasonForVisit, "reasonForVisit should not be null");
        this.patientName = patientName;
        this.symptoms = symptoms;
        this.doctorName = doctorName;
        this.location = location;
        this.email = email;
        this.date = date;
        this.time = time;
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
}
