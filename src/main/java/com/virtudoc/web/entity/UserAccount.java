package com.virtudoc.web.entity;

import com.virtudoc.web.dto.NewUserDTO;
import javax.validation.constraints.Email;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Defines a user account entity as stored in the database.
 *
 * @author ARMmaster17
 */
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String role;

    @NotNull
    @NotEmpty
    @Size(min = 1)
    private String password;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 1, max = 32)
    private String username;


    @Column(unique = true)
    @Email
    @Size(max = 320) // Specified in RFC 2821 and RFC 3693.
    private String email;


    private String firstName;


    private String lastName;


    private String gender;


    private Date birthDate;


    private boolean verified;

    /**
     * Required for serialization. Do not use directly.
     */
    public UserAccount() {

    }

    /**
     * Constructor for UserAccount class. Creation of this object does not save the user into the database.
     * @param username Unique identifier for the user.
     * @param password User password (may not be hashed depending on the context).
     * @param role GrantedAuthority compatible string identifier for user ACL access rights.
     */
    public UserAccount(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor for UserAccount class. Utilizes the NewUserDTO to ensure that protected fields
     * such as ROLE are not exposed in the controller.
     * @param userDTO DTO object from frontend Model.
     * @throws Exception Given password pair does not match.
     * @see NewUserDTO
     */
    public UserAccount(NewUserDTO userDTO) throws Exception {
        if (!userDTO.getPassword().equals(userDTO.getConfirmedPassword())) {
            throw new Exception("passwords do not match");
        }

        this.role = userDTO.getRole();
        this.password = userDTO.getPassword();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.gender = userDTO.getGender();
        this.birthDate = userDTO.getBirthDate();
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

    /**
     * Sets the user's password. WARNING: this method should not be used directly. Use the AuthenticationService
     * to properly hash a user's password before committing it to the database.
     * @param password User's new hashed password.
     * @see com.virtudoc.web.service.AuthenticationService
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Utilized by the GrantedAuthority class in the Spring Security framework.
     * @return The string representation of the user's ACL access rights.
     */
    public String getRole() {
        return role;
    }

    /**
     * Utilized by the GrantedAuthority class in the Spring Security framework.
     * @param role The string representation of the user's ACL access rights.
     */
    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void verify(){ this.verified = true; }

    public boolean isVerified(){ return verified; }
}
