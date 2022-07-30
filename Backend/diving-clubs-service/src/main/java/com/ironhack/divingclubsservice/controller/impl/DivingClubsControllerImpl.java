package com.ironhack.divingclubsservice.controller.impl;

import com.ironhack.divingclubsservice.controller.interfaces.DivingClubsController;
import com.ironhack.divingclubsservice.model.Club;
import com.ironhack.divingclubsservice.repository.ClubRepository;
import com.ironhack.divingclubsservice.service.interfaces.DivingClubsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DivingClubsControllerImpl implements DivingClubsController {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private DivingClubsService divingClubsService;

    @GetMapping("/clubs")
    @ResponseStatus(HttpStatus.OK)
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @GetMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Club getClub(@PathVariable Long id) {
        return divingClubsService.getClub(id);
    }

    @PostMapping("/create/club")
    @ResponseStatus(HttpStatus.CREATED)
    public Club createClub(@RequestBody Club club) {
        return clubRepository.save(club);
    }

    @PutMapping("/modify-club/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyClub(@PathVariable Long id, @RequestBody Club club) {
        divingClubsService.modifyClub(id, club);
    }

    @DeleteMapping("/delete/club/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClub(@PathVariable Long id) {
        divingClubsService.deleteClub(id);
    }
}
