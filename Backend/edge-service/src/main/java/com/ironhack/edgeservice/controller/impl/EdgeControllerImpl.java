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
    private RoleRepository roleRepository;
    @Autowired
    private DivingBookClient bookClient;
    @Autowired
    private DivingPassportClient passportClient;
    @Autowired
    private DivingClubClient clubClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Users
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword(null);
        return userToDTO(user);
    }

    @GetMapping("/users/usernames")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsernames() {
        return userRepository.findUsernames();
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@AuthenticationPrincipal User user) {
        log.info("Ha entrado");
        User user1 = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        log.info("Login successful");
        user1.setPassword(null); // NEVER RETURN PASSWORD
        return user1;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
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

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User modifyUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        User originalUser = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User user = dtoToModel(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setId(userId);

        //Set default profile
//        user.setRole(new Role("DIVER"));
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null);
        return savedUser;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Delete Diving Book and Passport associated with this user
        bookClient.deleteDiveBook(id);
        passportClient.deletePassport(id);

        //Delete user
        userRepository.delete(user);
    }

    //Dive Book
    @GetMapping("/dive-books/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DiveBook getDiveBook(@PathVariable Long userId) {
        return bookClient.getDiveBook(userId);
    }

    @PostMapping("/add-dive/dive-book/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dive addDiveToDiveBook(@PathVariable Long userId, @RequestBody DiveDTO diveDTO) throws IOException {
        User user = userRepository.findById(userId).orElseThrow( () ->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        Dive dive =  bookClient.addDiveToDiveBook(userId,diveDTO);
        String clubEmail = clubClient.getClub(dive.getClubId()).getEmail();

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        String body = "{\"from\":{\"email\":\"my.diving.trip@workmail.com\"}," +
                "\"personalizations\":[{\"to\":[{\"email\":\""+clubEmail+"\"}]," +
                "\"dynamic_template_data\":{\"date\":\""+dive.getDate().toString()+"\",\"diveId\":\""
                +dive.getId().toString()+"\", \"firstName\":\""+ user.getFirstName()+"\",\"lastName\":\""+
                user.getLastName()+"\"}}],\"template_id\":" +
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
        return dive;
    }

    @PutMapping("/modify-dive/{diveId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyDiveToDiveBook(@PathVariable Long diveId, @RequestBody DiveDTO diveDTO) {
        bookClient.modifyDiveToDiveBook(diveId,diveDTO);
    }

    @GetMapping("/dive/{id}/validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String validateDive(@PathVariable Long id){
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(true);
        bookClient.changeConfirmation(id, diveConfirmationDTO);
        return "The dive has been validated. Thanks for your time :)";
    }

    @GetMapping("/dive/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String cancelDiveValidation(@PathVariable Long id){
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(false);
        bookClient.changeConfirmation(id, diveConfirmationDTO);
        return "The dive has not been validated :(";
    }

    //Passport
    @GetMapping("/passports/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@PathVariable Long userId) {
        return passportClient.getPassport(userId);
    }

    @PostMapping("/add-titulation/passport/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Titulation addTitulation(@PathVariable Long userId, @RequestBody TitulationDTO titulationDTO) {
        return passportClient.addTitulation(userId,titulationDTO);
    }

    @PutMapping("/modify-titulation/{titulationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyTitulation(@PathVariable Long titulationId, @RequestBody TitulationDTO titulationDTO) {
        passportClient.modifyTitulation(titulationId,titulationDTO);
    }

    //Club
    @GetMapping("/clubs")
    @ResponseStatus(HttpStatus.OK)
    public List<Club> getAllClubs(){
        return clubClient.getAllClubs();
    }
    @GetMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Club getClub(@PathVariable Long id) {
        return clubClient.getClub(id);
    }

    @PostMapping("/clubs")
    @ResponseStatus(HttpStatus.CREATED)
    public Club createClub(@RequestBody Club club) {
        return clubClient.createClub(club);
    }

    @PutMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyClub(@PathVariable Long id, @RequestBody Club club) {
        clubClient.modifyClub(id, club);
    }

    @DeleteMapping("/clubs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClub(@PathVariable Long id) {
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
}
