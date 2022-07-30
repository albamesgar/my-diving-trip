package com.ironhack.divingclubsservice.service.impl;

import com.ironhack.divingclubsservice.model.Club;
import com.ironhack.divingclubsservice.repository.ClubRepository;
import com.ironhack.divingclubsservice.service.interfaces.DivingClubsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DivingClubsServiceImpl implements DivingClubsService {
    @Autowired
    private ClubRepository clubRepository;

    public Club getClub(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        return club;
    }

    public void modifyClub(Long id, Club club) {
        Club originalClub = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        club.setId(id);
        clubRepository.save(club);
    }

    public void deleteClub(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        clubRepository.delete(club);
    }
}
