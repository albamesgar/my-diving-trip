package com.ironhack.divingpassportservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
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

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Date getDateObtained() {
        return dateObtained;
    }

    public void setDateObtained(Date dateObtained) {
        this.dateObtained = dateObtained;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
