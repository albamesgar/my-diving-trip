package com.ironhack.edgeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
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
}
