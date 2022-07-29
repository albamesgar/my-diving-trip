package com.ironhack.edgeservice.controller.dto;

import java.sql.Date;

public class TitulationDTO {
    private String organization;
    private String titleName;
    private Date dateObtained;
    private String instructorName;
    private Long clubId;

    //Constructors

    public TitulationDTO() {
    }

    public TitulationDTO(String organization, String titleName, Date dateObtained, String instructorName, Long clubId) {
        this.organization = organization;
        this.titleName = titleName;
        this.dateObtained = dateObtained;
        this.instructorName = instructorName;
        this.clubId = clubId;
    }

    //Getters and Setters

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
}
