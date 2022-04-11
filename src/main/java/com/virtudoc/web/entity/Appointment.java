package com.virtudoc.web.entity;

import com.virtudoc.web.dto.appointmentDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private int appointmentId;

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

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String reasonForVisit;

    @Column(nullable = false)
    private boolean approved;

    public Appointment(){

    }

    public Appointment(
            String patientName,
            String doctorName,
            String email,
            String location,
            String symptoms,
            Date date,
            String reasonForVisit,
            boolean approved
    ){
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.email = email;
        this.location = location;
        this.date = date;
        this.symptoms = symptoms;
        this.reasonForVisit = reasonForVisit;
        this.approved = false;
    }

    public Appointment(appointmentDTO appointmentDTO){
        this.patientName = appointmentDTO.getPatientName();
        this.doctorName = appointmentDTO.getDoctorName();
        this.email = appointmentDTO.getEmail();
        this.location = appointmentDTO.getLocation();
        this.date = Date.from(LocalDateTime.parse(appointmentDTO.getDate()).toInstant(ZoneOffset.UTC));
        this.symptoms = appointmentDTO.getSymptoms();
        this.reasonForVisit = appointmentDTO.getReasonForVisit();
        this.approved = false;
    }


    public int getAppointmentId() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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
                ", reasonForVisit='" + reasonForVisit + '\'' +
                '}';
    }
}

