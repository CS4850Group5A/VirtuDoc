package com.virtudoc.web.dto;

import java.util.Date;

public class NewUserDTO {
    private String role;
    private String password;
    private String confirmedPassword;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private boolean isVerified;
    private boolean isDeactivated;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isVerified() {return isVerified;}

    public void setVerified(boolean isVerified)
    {
        this.isVerified = isVerified;
    }
    public void setDeactivated()
    {
        this.isDeactivated = isDeactivated;
    }
    public boolean isDeactivated()
    {
        return isDeactivated;
    }
}
