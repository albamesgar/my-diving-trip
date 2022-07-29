package com.ironhack.edgeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Dive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Date date;
    @NotNull
    private String location;
    @NotNull
    private double maxDepth;
    @NotNull
    private int minutesIn;
    private int airEntering;
    private int airOutgoing;
    private double temperature;
    private double visibility;
    private String bottleCapacity;
    private String airType;
    private int oxygenProportion;
    @NotNull
    private String partnerName;
    @NotNull
    private String partnerTitulation;
    private Long clubId;
    private int rating;
    private String observations;
    private String picture;
    private boolean clubValidation;

    @ManyToOne
    @JsonIgnore
    private DiveBook diveBook;

    //Constructors
    public Dive() {
    }

    public Dive(Date date, String location, double maxDepth, int minutesIn, int airEntering, int airOutgoing,
                double temperature, double visibility, String bottleCapacity, String airType,
                int oxygenProportion, String partnerName, String partnerTitulation, Long clubId, int rating,
                String observations, String picture, boolean clubValidation,DiveBook diveBook) {
        this.date = date;
        this.location = location;
        this.maxDepth = maxDepth;
        this.minutesIn = minutesIn;
        this.airEntering = airEntering;
        this.airOutgoing = airOutgoing;
        this.temperature = temperature;
        this.visibility = visibility;
        this.bottleCapacity = bottleCapacity;
        this.airType = airType;
        this.oxygenProportion = oxygenProportion;
        this.partnerName = partnerName;
        this.partnerTitulation = partnerTitulation;
        this.clubId = clubId;
        this.rating = rating;
        this.observations = observations;
        this.picture = picture;
        this.clubValidation = clubValidation;
        this.diveBook = diveBook;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public DiveBook getDiveBook() {
        return diveBook;
    }

    public void setDiveBook(DiveBook diveBook) {
        this.diveBook = diveBook;
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

    public String getPartnerTitulation() {
        return partnerTitulation;
    }

    public void setPartnerTitulation(String partnerTitulation) {
        this.partnerTitulation = partnerTitulation;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isClubValidation() {
        return clubValidation;
    }

    public void setClubValidation(boolean clubValidation) {
        this.clubValidation = clubValidation;
    }
}
