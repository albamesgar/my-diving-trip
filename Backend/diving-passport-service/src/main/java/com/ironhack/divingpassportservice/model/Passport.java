package com.ironhack.divingpassportservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @OneToMany(mappedBy = "passport", fetch = FetchType.EAGER)
    private List<Titulation> titulations = new ArrayList<>();

    //Constructors

    public Passport() {
    }

    public Passport(Long userId) {
        this.userId = userId;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Titulation> getTitulations() {
        return titulations;
    }

    public void setTitulations(List<Titulation> titulations) {
        this.titulations = titulations;
    }
}
