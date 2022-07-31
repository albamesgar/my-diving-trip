package com.ironhack.edgeservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiveBook diveBook = (DiveBook) o;
        return userId.equals(diveBook.userId) && dives.equals(diveBook.dives);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dives);
    }
}
