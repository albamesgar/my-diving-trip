package com.ironhack.edgeservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String street;
    private int homeNumber;
    private String city;
    private int postalCode;
    private Long contactPhone;
    private String country;
    private String email;
    private double rating;

    //Constructors
    public Club() {
    }

    public Club(String name, String street, int homeNumber, String city, int postalCode, Long contactPhone,
                String country, String email, double rating) {
        this.name = name;
        this.street = street;
        this.homeNumber = homeNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.contactPhone = contactPhone;
        this.country = country;
        this.email = email;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return homeNumber == club.homeNumber && postalCode == club.postalCode &&
                Double.compare(club.rating, rating) == 0 && name.equals(club.name) &&
                street.equals(club.street) && city.equals(club.city) && contactPhone.equals(club.contactPhone) &&
                country.equals(club.country) && email.equals(club.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, street, homeNumber, city, postalCode, contactPhone, country, email, rating);
    }
}
