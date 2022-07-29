package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.DiveDTO;
import com.ironhack.edgeservice.controller.dto.TitulationDTO;
import com.ironhack.edgeservice.controller.dto.UserDTO;
import com.ironhack.edgeservice.model.*;

import java.io.IOException;
import java.util.List;

public interface EdgeController {
    //Users
//    UserDTO getUser(Long id);
    List<String> getAllUsernames();
    UserDTO registerUser(UserDTO userDTO);
    User loginUser(User user);
    User modifyUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);

    //Dive Book
    DiveBook getDiveBook(Long id);
    Dive addDiveToDiveBook(Long id, DiveDTO diveDTO) throws IOException;
    void modifyDiveToDiveBook(Long id, DiveDTO diveDTO);
    String validateDive(Long id);
    String cancelDiveValidation(Long id);

    //Passport
    Passport getPassport(Long id);
    Titulation addTitulation(Long id, TitulationDTO titulationDTO);
    void modifyTitulation(Long id, TitulationDTO titulationDTO);

    //Clubs
    List<Club> getAllClubs();
    Club getClub(Long id);
//    Club getClubByName(String name);
    Club createClub(Club club);
    void modifyClub(Long id, Club club);
    void deleteClub(Long id);
}
