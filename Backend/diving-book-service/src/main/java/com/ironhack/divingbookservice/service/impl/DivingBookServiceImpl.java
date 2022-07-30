package com.ironhack.divingbookservice.service.impl;

import com.ironhack.divingbookservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.divingbookservice.controller.dto.DiveDTO;
import com.ironhack.divingbookservice.model.Dive;
import com.ironhack.divingbookservice.model.DiveBook;
import com.ironhack.divingbookservice.repository.DiveBookRepository;
import com.ironhack.divingbookservice.repository.DiveRepository;
import com.ironhack.divingbookservice.service.interfaces.DivingBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;

@Service
public class DivingBookServiceImpl implements DivingBookService {
    @Autowired
    private DiveBookRepository diveBookRepository;
    @Autowired
    private DiveRepository diveRepository;

    public DiveBook getDiveBook(Long userId) {
        DiveBook diveBook = diveBookRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Dive Book not found"));
        return diveBook;
    }

    public Dive addDiveToDiveBook(Long userId, DiveDTO diveDTO) {
        DiveBook diveBook = diveBookRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Dive Book not found"));
        Date date = diveDTO.getDate();
        String location = diveDTO.getLocation();
        double maxDepth = diveDTO.getMaxDepth();
        int minutesIn = diveDTO.getMinutesIn();
        String partnerName = diveDTO.getPartnerName();
        String partnerTitulation = diveDTO.getPartnerTitulation();

        int airEntering = diveDTO.getAirEntering();
        int airOutgoing = diveDTO.getAirOutgoing();
        double temperature = diveDTO.getTemperature();
        double visibility = diveDTO.getVisibility();
        int oxygenProportion = diveDTO.getOxygenProportion();
        Long clubId = diveDTO.getClubId();
        String picture = diveDTO.getPicture();
        String observations = diveDTO.getObservations();
        int rating = diveDTO.getRating();
        String bottleCapacity = diveDTO.getBottleCapacity();
        String airType = diveDTO.getAirType();
        boolean clubValidation = diveDTO.isClubValidation();

        Dive dive = new Dive(date, location, maxDepth,minutesIn,airEntering, airOutgoing, temperature, visibility,
                bottleCapacity, airType, oxygenProportion, partnerName, partnerTitulation, clubId, rating,
                observations, picture, clubValidation, diveBook);

        return diveRepository.save(dive);
    }

    public void modifyDiveToDiveBook(Long id, DiveDTO diveDTO) {
        Dive originalDive = diveRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Dive not found"));
        DiveBook diveBook = originalDive.getDiveBook();

        Date date = diveDTO.getDate();
        String location = diveDTO.getLocation();
        double maxDepth = diveDTO.getMaxDepth();
        int minutesIn = diveDTO.getMinutesIn();
        String partnerName = diveDTO.getPartnerName();
        String partnerTitulation = diveDTO.getPartnerTitulation();

        int airEntering = diveDTO.getAirEntering();
        int airOutgoing = diveDTO.getAirOutgoing();
        double temperature = diveDTO.getTemperature();
        double visibility = diveDTO.getVisibility();
        int oxygenProportion = diveDTO.getOxygenProportion();
        Long clubId = diveDTO.getClubId();
        String picture = diveDTO.getPicture();
        String observations = diveDTO.getObservations();
        int rating = diveDTO.getRating();
        String bottleCapacity = diveDTO.getBottleCapacity();
        String airType = diveDTO.getAirType();
        boolean clubValidation = diveDTO.isClubValidation();

        Dive dive = new Dive(date, location, maxDepth,minutesIn,airEntering, airOutgoing, temperature, visibility,
                bottleCapacity, airType, oxygenProportion, partnerName, partnerTitulation, clubId, rating,
                observations, picture, clubValidation, diveBook);
        dive.setId(id);

        diveRepository.save(dive);
    }

    public void changeConfirmation(Long id, DiveConfirmationDTO diveConfirmationDTO) {
        Dive dive = diveRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Dive not found"));
        dive.setClubValidation(diveConfirmationDTO.isClubConfirmation());
        diveRepository.save(dive);
        diveBookRepository.save(dive.getDiveBook());
    }

    public void deleteDiveBook(Long userId) {
        DiveBook diveBook = diveBookRepository.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Dive Book not found"));
        for (Dive dive: diveBook.getDives()){
            diveRepository.delete(dive);
        }
        diveBookRepository.delete(diveBook);
    }
}
