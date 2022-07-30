package com.ironhack.edgeservice.service.impl;

import com.ironhack.edgeservice.client.DivingBookClient;
import com.ironhack.edgeservice.client.DivingClubClient;
import com.ironhack.edgeservice.client.DivingPassportClient;
import com.ironhack.edgeservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.edgeservice.controller.dto.DiveDTO;
import com.ironhack.edgeservice.controller.dto.TitulationDTO;
import com.ironhack.edgeservice.controller.dto.UserDTO;
import com.ironhack.edgeservice.model.*;
import com.ironhack.edgeservice.repository.RoleRepository;
import com.ironhack.edgeservice.repository.UserRepository;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class EdgeServiceImpl implements EdgeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DivingBookClient bookClient;
    @Autowired
    private DivingPassportClient passportClient;
    @Autowired
    private DivingClubClient clubClient;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword(null);
        return userToDTO(user);
    }

    public User loginUser(User user) {
        User user1 = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user1.setPassword(null); // NEVER RETURN PASSWORD
        return user1;
    }

    @CircuitBreaker(name = "registerUser", fallbackMethod = "registerUserFallback" )
    public UserDTO registerUser(UserDTO userDTO) {
        User user = dtoToModel(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //Set default profile
        User savedUser = userRepository.save(user);

        //Create Diving Book and Passport associated with this user
        bookClient.createDiveBook(new DiveBook(savedUser.getId()));
        passportClient.createPassport(new Passport(savedUser.getId()));

        savedUser.setPassword(null);
        return userToDTO(savedUser);
    }

    public User modifyUser(Long userId, UserDTO userDTO) {
        User originalUser = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User user = dtoToModel(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setId(userId);

        User savedUser = userRepository.save(user);
        savedUser.setPassword(null);
        return savedUser;
    }

    @CircuitBreaker(name = "deleteUser", fallbackMethod = "deleteUserFallback" )
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Delete Diving Book and Passport associated with this user
        bookClient.deleteDiveBook(id);
        passportClient.deletePassport(id);

        //Delete user
        userRepository.delete(user);
    }

    @CircuitBreaker(name = "getDiveBook", fallbackMethod = "getDiveBookFallback" )
    public DiveBook getDiveBook(Long userId){
        return bookClient.getDiveBook(userId);
    }

    @CircuitBreaker(name = "addDiveToDiveBook", fallbackMethod = "addDiveToDiveBookFallback" )
    public Dive addDiveToDiveBook(Long userId, DiveDTO diveDTO) throws IOException {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        Dive dive =  bookClient.addDiveToDiveBook(userId,diveDTO);

        if (dive.getClubId() != null) {
            String clubEmail = clubClient.getClub(dive.getClubId()).getEmail();

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();

            String body = "{\"from\":{\"email\":\"my.diving.trip@workmail.com\"}," +
                    "\"personalizations\":[{\"to\":[{\"email\":\"" + clubEmail + "\"}]," +
                    "\"dynamic_template_data\":{\"date\":\"" + dive.getDate().toString() + "\",\"diveId\":\""
                    + dive.getId().toString() + "\", \"firstName\":\"" + user.getFirstName() + "\",\"lastName\":\"" +
                    user.getLastName() + "\"}}],\"template_id\":" +
                    "\"d-54ce21c55e5b4cfd95eecba2a0880346\"}";

            try {
                request.setMethod(Method.POST);
                request.setEndpoint("/mail/send");
                request.setBody(body);
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                throw ex;
            }
        }
        return dive;
    }

    @CircuitBreaker(name = "modifyDiveToDiveBook", fallbackMethod = "modifyDiveToDiveBookFallback" )
    public void modifyDiveToDiveBook(Long diveId, DiveDTO diveDTO){
        bookClient.modifyDiveToDiveBook(diveId,diveDTO);
    }

    @CircuitBreaker(name = "validateDive", fallbackMethod = "validateDiveFallback" )
    public String validateDive(Long id) {
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(true);
        bookClient.changeConfirmation(id, diveConfirmationDTO);
        return "The dive has been validated. Thanks for your time :)";
    }

    @CircuitBreaker(name = "cancelDiveValidation", fallbackMethod = "cancelDiveValidationFallback" )
    public String cancelDiveValidation(Long id) {
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(false);
        bookClient.changeConfirmation(id, diveConfirmationDTO);
        return "The dive has not been validated :(";
    }

    @CircuitBreaker(name = "getPassport", fallbackMethod = "getPassportFallback" )
    public Passport getPassport(Long userId){
        return passportClient.getPassport(userId);
    }

    @CircuitBreaker(name = "addTitulation", fallbackMethod = "addTitulationFallback" )
    public Titulation addTitulation(Long userId, TitulationDTO titulationDTO){
        return passportClient.addTitulation(userId,titulationDTO);
    }

    @CircuitBreaker(name = "modifyTitulation", fallbackMethod = "modifyTitulationFallback" )
    public void modifyTitulation(Long titulationId, TitulationDTO titulationDTO){
        passportClient.modifyTitulation(titulationId,titulationDTO);
    }

    @CircuitBreaker(name = "getAllClubs", fallbackMethod = "getAllClubsFallback" )
    public List<Club> getAllClubs(){
        return clubClient.getAllClubs();
    }

    @CircuitBreaker(name = "getClub", fallbackMethod = "getClubFallback" )
    public Club getClub(Long id){
        return clubClient.getClub(id);
    }

    @CircuitBreaker(name = "createClub", fallbackMethod = "createClubFallback" )
    public Club createClub(Club club){
        return clubClient.createClub(club);
    }

    @CircuitBreaker(name = "modifyClub", fallbackMethod = "modifyClubFallback" )
    public void modifyClub(Long id, Club club){
        clubClient.modifyClub(id, club);
    }

    @CircuitBreaker(name = "deleteClub", fallbackMethod = "deleteClubFallback" )
    public void deleteClub(Long id){
        clubClient.deleteClub(id);
    }

    private UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().getName());

        return userDTO;
    }

    private User dtoToModel(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setEmail(userDTO.getEmail());
        user.setProfilePicture(userDTO.getProfilePicture());
        Role role = roleRepository.findRoleByName(userDTO.getRole()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role doesn't exist"));
        user.setRole(role);

        return user;
    }

    //Fallback methods
    public UserDTO registerUserFallback(UserDTO userDTO, Exception e) {
        throw new RuntimeException(e.getMessage());
    }

    public void deleteUserFallback(Long id, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public DiveBook getDiveBookFallback(Long userId, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public Dive addDiveToDiveBook(Long userId, DiveDTO diveDTO, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public void modifyDiveToDiveBook(Long diveId, DiveDTO diveDTO, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public String validateDive(Long id, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public String cancelDiveValidation(Long id, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public Passport getPassport(Long userId, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public Titulation addTitulation(Long userId, TitulationDTO titulationDTO, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public void modifyTitulation(Long titulationId, TitulationDTO titulationDTO, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public List<Club> getAllClubs(Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public Club getClub(Long id, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public Club createClub(Club club, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public void modifyClub(Long id, Club club, Exception e){
        throw new RuntimeException(e.getMessage());
    }

    public void deleteClub(Long id, Exception e){
        throw new RuntimeException(e.getMessage());
    }
}
