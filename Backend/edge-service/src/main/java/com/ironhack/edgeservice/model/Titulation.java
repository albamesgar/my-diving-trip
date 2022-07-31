package com.ironhack.edgeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
public class Titulation {
    @Id
    @GeneratedValue
    private Long id;
    private String organization;
    private String titleName;
    private Date dateObtained;
    private String instructorName;
    private Long clubId;

    @ManyToOne
    @JsonIgnore
    private Passport passport;

    //Constructors

    public Titulation() {
    }

    public Titulation(String organization, String titleName, Date dateObtained, String instructorName,
                      Long clubId, Passport passport) {
        this.organization = organization;
        this.titleName = titleName;
        this.dateObtained = dateObtained;
        this.instructorName = instructorName;
        this.clubId = clubId;
        this.passport = passport;
    }
}
