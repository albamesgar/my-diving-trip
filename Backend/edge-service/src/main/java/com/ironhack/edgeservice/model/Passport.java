package com.ironhack.edgeservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @OneToMany(mappedBy = "passport")
    private List<Titulation> titulations = new ArrayList<>();

    //Constructors

    public Passport() {
    }

    public Passport(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return userId.equals(passport.userId) && titulations.equals(passport.titulations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, titulations);
    }
}
