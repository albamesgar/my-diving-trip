package com.ironhack.divingclubsservice.controller.impl;

import com.ironhack.divingclubsservice.controller.interfaces.DivingClubsController;
import com.ironhack.divingclubsservice.model.Club;
import com.ironhack.divingclubsservice.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DivingClubsControllerImpl implements DivingClubsController {
    @Autowired
    private ClubRepository clubRepository;

    @GetMapping("/clubs")
    @ResponseStatus(HttpStatus.OK)
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @GetMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Club getClub(@PathVariable Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        return club;
    }

//    @GetMapping("/clubs/{name}")
//    @ResponseStatus(HttpStatus.OK)
//    public Club getClubByName(@PathVariable String name) {
//        Club club = clubRepository.findClubByName(name).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
//        return club;
//    }

    @PostMapping("/create/club")
    @ResponseStatus(HttpStatus.CREATED)
    public Club createClub(@RequestBody Club club) {
        return clubRepository.save(club);
    }

    @PutMapping("/modify-club/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyClub(@PathVariable Long id, @RequestBody Club club) {
        Club originalClub = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        club.setId(id);
        clubRepository.save(club);
    }

    @DeleteMapping("/delete/club/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClub(@PathVariable Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        clubRepository.delete(club);
    }
}
