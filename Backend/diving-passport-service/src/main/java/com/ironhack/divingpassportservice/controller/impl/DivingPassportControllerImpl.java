package com.ironhack.divingpassportservice.controller.impl;

import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.controller.interfaces.DivingPassportController;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;
import com.ironhack.divingpassportservice.repository.PassportRepository;
import com.ironhack.divingpassportservice.repository.TitulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;

@RestController
public class DivingPassportControllerImpl implements DivingPassportController {
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private TitulationRepository titulationRepository;

    @GetMapping("/passport/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@PathVariable Long userId) {
        Passport passport = passportRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found"));
        return passport;
    }

    @PostMapping("/create/passport")
    @ResponseStatus(HttpStatus.CREATED)
    public Passport createPassport(@RequestBody Passport passport) {
        return passportRepository.save(passport);
    }

    @PostMapping("/add-titulation/passport/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Titulation addTitulation(@PathVariable Long userId, @RequestBody TitulationDTO titulationDTO) {
        Passport passport = passportRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found"));

        String titleName = titulationDTO.getTitleName();
        Date dateObtained = titulationDTO.getDateObtained();
        String instructorName = titulationDTO.getInstructorName();
        Long clubId = titulationDTO.getClubId();
        String organization = titulationDTO.getOrganization();

        Titulation titulation = new Titulation(organization,titleName,dateObtained,instructorName,clubId,passport);

        return titulationRepository.save(titulation);
    }

    @PutMapping("/modify-titulation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyTitulation(@PathVariable Long id, @RequestBody TitulationDTO titulationDTO) {
        Titulation originalTitulation = titulationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Titulation not found"));
        Passport passport = originalTitulation.getPassport();

        String titleName = titulationDTO.getTitleName();
        Date dateObtained = titulationDTO.getDateObtained();
        String instructorName = titulationDTO.getInstructorName();
        Long clubId = titulationDTO.getClubId();
        String organization = titulationDTO.getOrganization();

        Titulation titulation = new Titulation(organization,titleName,dateObtained,instructorName,clubId,passport);
        titulation.setId(id);

        titulationRepository.save(titulation);
    }

    @DeleteMapping("/delete/passport/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassport(@PathVariable Long userId) {
        Passport passport = passportRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found"));
        for (Titulation titulation: passport.getTitulations()){
            titulationRepository.delete(titulation);
        }
        passportRepository.delete(passport);
    }
}
