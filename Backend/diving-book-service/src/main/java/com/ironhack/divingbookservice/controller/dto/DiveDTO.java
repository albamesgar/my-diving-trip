package com.ironhack.divingbookservice.controller.dto;

import javax.validation.constraints.NotNull;
import java.sql.Date;

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

    //Getters and setters

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(double maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMinutesIn() {
        return minutesIn;
    }

    public void setMinutesIn(int minutesIn) {
        this.minutesIn = minutesIn;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerTitulation() {
        return partnerTitulation;
    }

    public void setPartnerTitulation(String partnerTitulation) {
        this.partnerTitulation = partnerTitulation;
    }

    public int getAirEntering() {
        return airEntering;
    }

    public void setAirEntering(int airEntering) {
        this.airEntering = airEntering;
    }

    public int getAirOutgoing() {
        return airOutgoing;
    }

    public void setAirOutgoing(int airOutgoing) {
        this.airOutgoing = airOutgoing;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public String getBottleCapacity() {
        return bottleCapacity;
    }

    public void setBottleCapacity(String bottleCapacity) {
        this.bottleCapacity = bottleCapacity;
    }

    public String getAirType() {
        return airType;
    }

    public void setAirType(String airType) {
        this.airType = airType;
    }

    public int getOxygenProportion() {
        return oxygenProportion;
    }

    public void setOxygenProportion(int oxygenProportion) {
        this.oxygenProportion = oxygenProportion;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isClubValidation() {
        return clubValidation;
    }

    public void setClubValidation(boolean clubValidation) {
        this.clubValidation = clubValidation;
    }
}
