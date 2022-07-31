package com.ironhack.edgeservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DiveConfirmationDTO {
    private boolean clubConfirmation;

    public DiveConfirmationDTO() {
    }

    public DiveConfirmationDTO(boolean clubConfirmation) {
        this.clubConfirmation = clubConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiveConfirmationDTO that = (DiveConfirmationDTO) o;
        return clubConfirmation == that.clubConfirmation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clubConfirmation);
    }
}
