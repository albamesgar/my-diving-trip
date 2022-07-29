package com.ironhack.divingpassportservice.controller.interfaces;

import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;

public interface DivingPassportController {
    Passport getPassport(Long id);
    Passport createPassport(Passport passport);
    Titulation addTitulation(Long id, TitulationDTO titulationDTO);
    void modifyTitulation(Long id, TitulationDTO titulationDTO);
    void deletePassport(Long id);
}
