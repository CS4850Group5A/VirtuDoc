package com.virtudoc.web.entity;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    //Foreign Key, links appointments table to parent user table
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String symptoms;

    @Column(nullable = false)
    private String location;
    //    todo: convert to Date object

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String reasonForVisit;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "patient_account_id", nullable = false)
    private UserAccount patientAccount;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserAccount getPatientAccount() {
        return patientAccount;
    }

    public void setPatientAccount(UserAccount patientAccount) {
        this.patientAccount = patientAccount;
    }

    public Appointment() {
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
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
                ", symptoms='" + symptoms + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", reasonForVisit='" + reasonForVisit + '\'' +
                '}';
    }
}
