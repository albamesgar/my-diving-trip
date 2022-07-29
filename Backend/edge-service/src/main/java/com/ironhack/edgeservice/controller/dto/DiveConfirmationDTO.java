package com.ironhack.edgeservice.controller.dto;

public class DiveConfirmationDTO {
    private boolean clubConfirmation;

    public DiveConfirmationDTO() {
    }

    public DiveConfirmationDTO(boolean clubConfirmation) {
        this.clubConfirmation = clubConfirmation;
    }

    public boolean isClubConfirmation() {
        return clubConfirmation;
    }

    public void setClubConfirmation(boolean clubConfirmation) {
        this.clubConfirmation = clubConfirmation;
    }
}
