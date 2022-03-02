package com.virtudoc.web.entity;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long appointmentId;

    //Foreign Key, links appointments table to parent user table
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String symptoms;

    @Column(nullable = false)
    private String doctorName;

    @Column(nullable = false)
    private String location;
    //    todo: convert to Date object

    @Column(nullable = false)
    private String date;

    @Column(nullable = false) //Added time var
    private String time;

    @Column(nullable = false)
    private String reasonForVisit;

    public Appointment() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getTime() { return time; }

    public void setTime(String time) { this.time=time; }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", email='" + email + '\'' +
                ", patientName='" + patientName + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", reasonForVisit='" + reasonForVisit + '\'' +
                '}';
    }

}


