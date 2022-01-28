package com.virtudoc.web.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username for the user
     */
    @NotNull
    @NotEmpty
    private String username;

    /**
     * Email address of the user
     */
    @NotNull
    @NotEmpty
    private String email;

    /**
     * First Name of the user
     */
    @NotNull
    @NotEmpty
    private String firstName;

    /**
     * Last Name of the user
     */
    @NotNull
    @NotEmpty
    private String lastName;

    /**
     * Password for the user
     */
    @NotNull
    @NotEmpty
    private String password;

    /**
     * Confirm Password for the user
     */
    @NotNull
    @NotEmpty
    private String confirmPassword;

    /**
     * Select Gender for the user
     */
    @NotNull
    @NotEmpty
    private String gender;

    /**
     * Date of Birth of the user
     */
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date DOB;


    @ManyToMany
    private Set<Role> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return confirmPassword;
    }

    public void setPasswordConfirm(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(@NotNull Date DOB) {
        this.DOB = DOB;
    }
}

