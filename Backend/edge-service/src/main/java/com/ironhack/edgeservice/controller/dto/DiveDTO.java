package com.ironhack.edgeservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
public class DiveDTO {
    @NotNull
    private Date date;
    @NotNull
    private String location;
    @NotNull
    private double maxDepth;
    @NotNull
    private int minutesIn;
    @NotNull
    private String partnerName;
    @NotNull
    private String partnerTitulation;

    private int airEntering;
    private int airOutgoing;
    private double temperature;
    private double visibility;
    private String bottleCapacity;
    private String airType;
    private int oxygenProportion;
    private Long clubId;
    private String picture;
    private String observations;
    private int rating;
    private boolean clubValidation;

    //Constructors

    public DiveDTO() {
    }

    public DiveDTO(Date date, String location, double maxDepth, int minutesIn, String partnerName,
                   String partnerTitulation, int airEntering, int airOutgoing, double temperature, double visibility,
                   String bottleCapacity, String airType, int oxygenProportion, Long clubId, String picture,
                   String observations, int rating, boolean clubValidation) {
        this.date = date;
        this.location = location;
        this.maxDepth = maxDepth;
        this.minutesIn = minutesIn;
        this.partnerName = partnerName;
        this.partnerTitulation = partnerTitulation;
        this.airEntering = airEntering;
        this.airOutgoing = airOutgoing;
        this.temperature = temperature;
        this.visibility = visibility;
        this.bottleCapacity = bottleCapacity;
        this.airType = airType;
        this.oxygenProportion = oxygenProportion;
        this.clubId = clubId;
        this.picture = picture;
        this.observations = observations;
        this.rating = rating;
        this.clubValidation = clubValidation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiveDTO diveDTO = (DiveDTO) o;
        return Double.compare(diveDTO.maxDepth, maxDepth) == 0 && minutesIn == diveDTO.minutesIn &&
                airEntering == diveDTO.airEntering && airOutgoing == diveDTO.airOutgoing &&
                Double.compare(diveDTO.temperature, temperature) == 0 &&
                Double.compare(diveDTO.visibility, visibility) == 0 && oxygenProportion == diveDTO.oxygenProportion &&
                rating == diveDTO.rating && clubValidation == diveDTO.clubValidation && date.equals(diveDTO.date) &&
                location.equals(diveDTO.location) && partnerName.equals(diveDTO.partnerName) &&
                partnerTitulation.equals(diveDTO.partnerTitulation) &&
                Objects.equals(bottleCapacity, diveDTO.bottleCapacity) && Objects.equals(airType, diveDTO.airType) &&
                Objects.equals(clubId, diveDTO.clubId) && Objects.equals(picture, diveDTO.picture) &&
                Objects.equals(observations, diveDTO.observations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, location, maxDepth, minutesIn, partnerName, partnerTitulation,
                airEntering, airOutgoing, temperature, visibility, bottleCapacity, airType, oxygenProportion,
                clubId, picture, observations, rating, clubValidation);
    }
}
