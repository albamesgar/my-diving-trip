package com.ironhack.divingpassportservice.controller.impl;

import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.controller.interfaces.DivingPassportController;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;
import com.ironhack.divingpassportservice.repository.PassportRepository;
import com.ironhack.divingpassportservice.repository.TitulationRepository;
import com.ironhack.divingpassportservice.service.interfaces.DivingPassportService;
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
    @Autowired
    private DivingPassportService divingPassportService;

    @GetMapping("/passport/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@PathVariable Long userId) {
        return divingPassportService.getPassport(userId);
    }

    @PostMapping("/create/passport")
    @ResponseStatus(HttpStatus.CREATED)
    public Passport createPassport(@RequestBody Passport passport) {
        return passportRepository.save(passport);
    }

    @PostMapping("/add-titulation/passport/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Titulation addTitulation(@PathVariable Long userId, @RequestBody TitulationDTO titulationDTO) {
        return divingPassportService.addTitulation(userId, titulationDTO);
    }

    @PutMapping("/modify-titulation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyTitulation(@PathVariable Long id, @RequestBody TitulationDTO titulationDTO) {
        divingPassportService.modifyTitulation(id, titulationDTO);
    }

    @DeleteMapping("/delete/passport/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassport(@PathVariable Long userId) {
        divingPassportService.deletePassport(userId);
    }
}
