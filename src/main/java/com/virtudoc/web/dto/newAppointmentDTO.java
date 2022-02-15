package com.virtudoc.web.dto;

public class newAppointmentDTO {
    private String patientName;
    private String email;
    private String symptoms;
    private String doctorName;
    private String location;
    private String date;
    private String time;
    private String reasonForVisit;

    public String getPatientName() {

        return patientName;
    }

    public void setPatientName(String FullName) {

        this.patientName = FullName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String Email) {

        this.email = Email;
    }


    public String getSymptoms() {

        return symptoms;
    }

    public void setSymptoms(String Symptoms) {

        this.symptoms = Symptoms;
    }

    public String getDoctorName() {

        return doctorName;
    }

    public void setDoctorName(String Doctor) {

        this.doctorName = Doctor;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String Location) {

        this.location = Location;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String AppointmentDate) {

        this.date = AppointmentDate;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String AppointmentTime) {

        this.time = AppointmentTime;
    }

    public String getReasonForVisit() {

        return reasonForVisit;
    }

    public void setReasonForVisit(String Comment) {

        this.reasonForVisit = Comment;
    }


// Override toString()

    @Override
    public String toString() {
        return "newAppointmentDTO{" +
                "patientName='" + patientName + '\'' +
                ", email='" + email + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", reasonForVisit='" + reasonForVisit + '\'' +
                '}';
    }
}
