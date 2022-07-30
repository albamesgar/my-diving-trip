package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.DiveDTO;
import com.ironhack.edgeservice.controller.dto.TitulationDTO;
import com.ironhack.edgeservice.controller.dto.UserDTO;
import com.ironhack.edgeservice.model.*;

import java.io.IOException;
import java.util.List;

public interface EdgeService {
    UserDTO getUser(Long id);
    User loginUser(User user);
    UserDTO registerUser(UserDTO userDTO);
    User modifyUser(Long userId, UserDTO userDTO);
    void deleteUser(Long id);

    DiveBook getDiveBook(Long userId);
    Dive addDiveToDiveBook(Long userId, DiveDTO diveDTO) throws IOException;
    void modifyDiveToDiveBook(Long diveId, DiveDTO diveDTO);
    String validateDive(Long id);
    String cancelDiveValidation(Long id);

    Passport getPassport(Long userId);
    Titulation addTitulation(Long userId, TitulationDTO titulationDTO);
    void modifyTitulation(Long titulationId, TitulationDTO titulationDTO);

    List<Club> getAllClubs();
    Club getClub(Long id);
    Club createClub(Club club);
    void modifyClub(Long id, Club club);
    void deleteClub(Long id);
}
