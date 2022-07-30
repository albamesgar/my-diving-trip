package com.ironhack.edgeservice.controller.impl;

import com.ironhack.edgeservice.client.DivingBookClient;
import com.ironhack.edgeservice.client.DivingClubClient;
import com.ironhack.edgeservice.client.DivingPassportClient;
import com.ironhack.edgeservice.controller.dto.*;
import com.ironhack.edgeservice.controller.interfaces.EdgeController;
import com.ironhack.edgeservice.model.*;
import com.ironhack.edgeservice.repository.RoleRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.security.CustomUserDetails;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EdgeControllerImpl implements EdgeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EdgeService edgeService;

    //Users
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable Long id) {
        return edgeService.getUser(id);
    }

    @GetMapping("/users/usernames")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsernames() {
        return userRepository.findUsernames();
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@AuthenticationPrincipal User user) {
        return edgeService.loginUser(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return edgeService.registerUser(userDTO);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User modifyUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        return edgeService.modifyUser(userId,userDTO);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        edgeService.deleteUser(id);
    }

    //Dive Book
    @GetMapping("/dive-books/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DiveBook getDiveBook(@PathVariable Long userId) {
        return edgeService.getDiveBook(userId);
    }

    @PostMapping("/add-dive/dive-book/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dive addDiveToDiveBook(@PathVariable Long userId, @RequestBody DiveDTO diveDTO) throws IOException {
        return edgeService.addDiveToDiveBook(userId, diveDTO);
    }

    @PutMapping("/modify-dive/{diveId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyDiveToDiveBook(@PathVariable Long diveId, @RequestBody DiveDTO diveDTO) {
        edgeService.modifyDiveToDiveBook(diveId,diveDTO);
    }

    @GetMapping("/dive/{id}/validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String validateDive(@PathVariable Long id){
        return edgeService.validateDive(id);
    }

    @GetMapping("/dive/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String cancelDiveValidation(@PathVariable Long id){
        return edgeService.cancelDiveValidation(id);
    }

    //Passport
    @GetMapping("/passports/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@PathVariable Long userId) {
        return edgeService.getPassport(userId);
    }

    @PostMapping("/add-titulation/passport/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Titulation addTitulation(@PathVariable Long userId, @RequestBody TitulationDTO titulationDTO) {
        return edgeService.addTitulation(userId,titulationDTO);
    }

    @PutMapping("/modify-titulation/{titulationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyTitulation(@PathVariable Long titulationId, @RequestBody TitulationDTO titulationDTO) {
        edgeService.modifyTitulation(titulationId,titulationDTO);
    }

    //Club
    @GetMapping("/clubs")
    @ResponseStatus(HttpStatus.OK)
    public List<Club> getAllClubs(){
        return edgeService.getAllClubs();
    }
    @GetMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Club getClub(@PathVariable Long id) {
        return edgeService.getClub(id);
    }

    @PostMapping("/clubs")
    @ResponseStatus(HttpStatus.CREATED)
    public Club createClub(@RequestBody Club club) {
        return edgeService.createClub(club);
    }

    @PutMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyClub(@PathVariable Long id, @RequestBody Club club) {
        edgeService.modifyClub(id, club);
    }

    @DeleteMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClub(@PathVariable Long id) {
        edgeService.deleteClub(id);
    }
}
