package com.ironhack.divingpassportservice.service.interfaces;

import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DivingPassportService {
    Passport getPassport(Long userId);
    Titulation addTitulation(Long userId, TitulationDTO titulationDTO);
    void modifyTitulation(Long id, TitulationDTO titulationDTO);
    void deletePassport(Long userId);
}
