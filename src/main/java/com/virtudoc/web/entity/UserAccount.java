package com.virtudoc.web.entity;

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

    @NotNull
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
}
