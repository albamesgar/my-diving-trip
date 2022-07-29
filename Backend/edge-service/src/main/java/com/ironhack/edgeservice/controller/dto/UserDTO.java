package com.ironhack.edgeservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.edgeservice.model.Role;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String profilePicture;
    private String role;

    //Constructors

    public UserDTO() {
    }

    public UserDTO(String username, String password, String firstName, String lastName, Date birthDate, String email,
                   String profilePicture, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    //Getters and Setters
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
