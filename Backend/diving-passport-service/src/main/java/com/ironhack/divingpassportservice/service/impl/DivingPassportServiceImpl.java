package com.ironhack.divingpassportservice.service.impl;

import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;
import com.ironhack.divingpassportservice.repository.PassportRepository;
import com.ironhack.divingpassportservice.repository.TitulationRepository;
import com.ironhack.divingpassportservice.service.interfaces.DivingPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;

@Service
public class DivingPassportServiceImpl implements DivingPassportService {
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private TitulationRepository titulationRepository;

    public Passport getPassport(Long userId) {
        Passport passport = passportRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found"));
        return passport;
    }

    public Titulation addTitulation(Long userId, TitulationDTO titulationDTO) {
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

    public void modifyTitulation(Long id, TitulationDTO titulationDTO) {
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

    public void deletePassport(Long userId) {
        Passport passport = passportRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found"));
        for (Titulation titulation: passport.getTitulations()){
            titulationRepository.delete(titulation);
        }
        passportRepository.delete(passport);
    }
}
