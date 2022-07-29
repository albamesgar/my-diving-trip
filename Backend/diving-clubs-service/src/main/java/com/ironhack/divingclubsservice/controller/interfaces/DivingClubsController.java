package com.ironhack.divingclubsservice.controller.interfaces;

import com.ironhack.divingclubsservice.model.Club;

import java.util.List;

public interface DivingClubsController {
    List<Club> getAllClubs();
    Club getClub(Long id);
//    Club getClubByName(String name);
    Club createClub(Club club);
    void modifyClub(Long id, Club club);
    void deleteClub(Long id);
}
