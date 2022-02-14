package com.virtudoc.web.dto;
import java.util.Date;

public class newAppointmentDTO {
    private String patientName;
    private String email;
    private String phone;
    private String symptoms;
    private String doctorName;
    private String location;
    private String date;
    private String time;
    private String reasonForVisit;

    public String getPatientName() {

        return patientName;
    }

    public void setPatientName(String patientName) {

        this.patientName = patientName;
    }
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }
    public String getSymptoms() {

        return symptoms;
    }

    public void setSymptoms(String symptoms) {

        this.symptoms = symptoms;
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

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time=time;
    }

    public String getReasonForVisit() {

        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {

        this.reasonForVisit = reasonForVisit;
    }
}
