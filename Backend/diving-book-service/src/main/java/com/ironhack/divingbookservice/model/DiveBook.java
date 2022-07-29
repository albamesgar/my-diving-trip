package com.ironhack.divingbookservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DiveBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @OneToMany(mappedBy = "diveBook")
    private List<Dive> dives = new ArrayList<>();

    //Constructors

    public DiveBook() {
    }

    public DiveBook(Long userId) {
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

    public List<Dive> getDives() {
        return dives;
    }

    public void setDives(List<Dive> dives) {
        this.dives = dives;
    }
}
