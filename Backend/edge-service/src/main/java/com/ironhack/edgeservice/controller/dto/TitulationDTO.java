package com.ironhack.edgeservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitulationDTO that = (TitulationDTO) o;
        return organization.equals(that.organization) && titleName.equals(that.titleName) && dateObtained.equals(that.dateObtained) && instructorName.equals(that.instructorName) && clubId.equals(that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, titleName, dateObtained, instructorName, clubId);
    }
}
