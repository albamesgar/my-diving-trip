package com.ironhack.edgeservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.edgeservice.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
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
}
