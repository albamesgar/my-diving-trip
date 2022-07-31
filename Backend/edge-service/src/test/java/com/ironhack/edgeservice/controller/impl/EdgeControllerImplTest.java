package com.ironhack.edgeservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.ClusterEnvironmentBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class EdgeControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ApplicationContext context;

    @MockBean
    private DivingBookClient mockBookClient;
    @MockBean
    private DivingPassportClient mockPassportClient;
    @MockBean
    private DivingClubClient mockClubClient;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Club club1, club2;
    Dive dive1, dive2;
    DiveBook diveBook1, diveBook2;
    Passport passport1, passport2;
    Titulation titulation1, titulation2;
    Role diver;
    User user1, user2;
    @BeforeEach
    void setUp() {
        diver = new Role("DIVER");
        user1 = new User("alba",passwordEncoder.encode("1234"),"Alba","Mesa",
                Date.valueOf("1997-03-29"),"alba@gmail.com","none",diver);
        user2 = new User("toni",passwordEncoder.encode("1234"),"Toni","Perez",
                Date.valueOf("1963-09-28"),"toni@gmail.com","none",diver);
        club1 = new Club("BlauMar", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        club1.setId(1L);
        club2 = new Club("Hesperides", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        club2.setId(2L);
        diveBook1 = new DiveBook(user1.getId());
        diveBook1.setId(1L);
        diveBook2 = new DiveBook(user2.getId());
        diveBook2.setId(2L);
        dive1 = new Dive(Date.valueOf("2022-07-15"),"Medas",22.5,55,200,
                70,19,10,"12L","Air",27,"Alba",
                "2**",null,4,"none","none",false, diveBook1);
        dive1.setId(1L);
        dive2 = new Dive(Date.valueOf("2022-07-30"),"random",22.5,55,200,
                70,19,10,"12L","Air",27,"Alba",
                "2**",club1.getId(),4,"none","none",true, diveBook2);
        dive2.setId(2L);
        diveBook1.setDives(List.of(dive1));
        diveBook2.setDives(List.of(dive2));
        passport1 = new Passport(user1.getId());
        passport1.setId(1L);
        passport2 = new Passport(user2.getId());
        passport2.setId(2L);
        titulation1 = new Titulation("FEDAS","1*", Date.valueOf("2022-07-30"),
                "random",1L,passport1);
        titulation1.setId(1L);
        titulation2 = new Titulation("FEDAS","2**", Date.valueOf("2022-07-30"),
                "random",1L,passport2);
        titulation2.setId(2L);
        passport1.setTitulations(List.of(titulation1));
        passport2.setTitulations(List.of(titulation2));

        roleRepository.save(diver);
        userRepository.saveAll(List.of(user1,user2));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void getUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/"+user1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alba"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Toni"));
    }

    @Test
    void getAllUsernames() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/usernames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("alba"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("toni"));
    }

    @Test
    void loginUser() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Basic YWxiYToxMjM0"); //username: alba, password: 1234

        MvcResult mvcResult = mockMvc.perform(get("/login").headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alba"));
    }

    @Test
    void registerUser() throws Exception {
        DiveBook diveBook = new DiveBook(user2.getId()+1);
        Mockito.when(mockBookClient.createDiveBook(diveBook)).thenReturn(diveBook);
        Passport passport = new Passport(user2.getId()+1);
        Mockito.when(mockPassportClient.createPassport(passport)).thenReturn(passport);

        UserDTO userDTO = new UserDTO("lia",passwordEncoder.encode("1234"),"Lia",
                "Perez",Date.valueOf("1995-04-22"),"lia@gmail.com","none","DIVER");
        String body = objectMapper.writeValueAsString(userDTO);

        MvcResult mvcResult = mockMvc.perform(
                        post("/users")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lia"));
        assertTrue(userRepository.count()==3);

        Mockito.verify(mockBookClient).createDiveBook(diveBook);
        Mockito.verify(mockPassportClient).createPassport(passport);
    }

    @Test
    void modifyUser() throws Exception {
        UserDTO userDTO = new UserDTO("lia",passwordEncoder.encode("1234"),"Lia",
                "Perez", Date.valueOf("1995-04-22"),"lia@gmail.com","none","DIVER");
        String body = objectMapper.writeValueAsString(userDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/users/"+user1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Lia"));
        User user = userRepository.findById(user1.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(user.getUsername().equals("lia"));
        assertTrue(userRepository.count()==2);
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.doNothing().when(mockBookClient).deleteDiveBook(user2.getId());
        Mockito.doNothing().when(mockPassportClient).deletePassport(user2.getId());

        MvcResult mvcResult = mockMvc.perform(
                        delete("/users/"+user2.getId())
                ).andExpect(status().isNoContent())
                .andReturn();
        assertTrue(userRepository.count()==1);

        Mockito.verify(mockBookClient).deleteDiveBook(user2.getId());
        Mockito.verify(mockPassportClient).deletePassport(user2.getId());
    }

    @Test
    void getDiveBook() throws Exception {
        Mockito.when(mockBookClient.getDiveBook(user1.getId()))
                .thenReturn(diveBook1);

        MvcResult mvcResult = mockMvc.perform(get("/dive-books/"+user1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Medas"));

        Mockito.verify(mockBookClient).getDiveBook(user1.getId());
    }

    @Test
    void addDiveToDiveBook() throws Exception {
        DiveDTO diveDTO = new DiveDTO(dive1.getDate(),dive1.getLocation(),dive1.getMaxDepth(),dive1.getMinutesIn(),
                dive1.getPartnerName(),dive1.getPartnerTitulation(),dive1.getAirEntering(),dive1.getAirOutgoing(),
                dive1.getTemperature(),dive1.getVisibility(),dive1.getBottleCapacity(),dive1.getAirType(),
                dive1.getOxygenProportion(),dive1.getClubId(),dive1.getPicture(),dive1.getObservations(),
                dive1.getRating(),dive1.isClubValidation());
        Mockito.when(mockBookClient.addDiveToDiveBook(user1.getId(),diveDTO))
                .thenReturn(dive1);
        String body = objectMapper.writeValueAsString(diveDTO);

        MvcResult mvcResult = mockMvc.perform(post("/add-dive/dive-book/"+user1.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Medas"));

        Mockito.verify(mockBookClient).addDiveToDiveBook(user1.getId(),diveDTO);
    }

    @Test
    void modifyDiveToDiveBook() throws Exception {
        DiveDTO diveDTO = new DiveDTO(dive1.getDate(),"Badalona",dive1.getMaxDepth(),dive1.getMinutesIn(),
                dive1.getPartnerName(),dive1.getPartnerTitulation(),dive1.getAirEntering(),dive1.getAirOutgoing(),
                dive1.getTemperature(),dive1.getVisibility(),dive1.getBottleCapacity(),dive1.getAirType(),
                dive1.getOxygenProportion(),dive1.getClubId(),dive1.getPicture(),dive1.getObservations(),
                dive1.getRating(),dive1.isClubValidation());
        String body = objectMapper.writeValueAsString(diveDTO);

        Mockito.doAnswer(inputs -> {
            dive1.setLocation(diveDTO.getLocation());
            return null;
        }).when(mockBookClient).modifyDiveToDiveBook(dive1.getId(), diveDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/modify-dive/"+dive1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();

        assertTrue(dive1.getLocation().equals("Badalona"));

        Mockito.verify(mockBookClient).modifyDiveToDiveBook(dive1.getId(), diveDTO);
    }

    @Test
    void validateDive() throws Exception {
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(true);

        Mockito.doAnswer(inputs -> {
            dive1.setClubValidation(diveConfirmationDTO.isClubConfirmation());
            return null;
        }).when(mockBookClient).changeConfirmation(dive1.getId(), diveConfirmationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        get("/dive/"+dive1.getId()+"/validate")
                ).andExpect(status().isNoContent())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("validated"));
        Mockito.verify(mockBookClient).changeConfirmation(dive1.getId(), diveConfirmationDTO);
    }

    @Test
    void cancelDiveValidation() throws Exception {
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(false);

        Mockito.doAnswer(inputs -> {
            dive1.setClubValidation(diveConfirmationDTO.isClubConfirmation());
            return null;
        }).when(mockBookClient).changeConfirmation(dive2.getId(), diveConfirmationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        get("/dive/"+dive2.getId()+"/cancel")
                ).andExpect(status().isNoContent())
                .andReturn();

        assertFalse(mvcResult.getResponse().getContentAsString().contains("not validated"));
        Mockito.verify(mockBookClient).changeConfirmation(dive2.getId(), diveConfirmationDTO);
    }

    @Test
    void getPassport() throws Exception {
        Mockito.when(mockPassportClient.getPassport(user1.getId()))
                .thenReturn(passport1);

        MvcResult mvcResult = mockMvc.perform(get("/passports/"+user1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1*"));

        Mockito.verify(mockPassportClient).getPassport(user1.getId());
    }

    @Test
    void addTitulation() throws Exception {
        TitulationDTO titulationDTO = new TitulationDTO(titulation1.getOrganization(),titulation1.getTitleName(),
                titulation1.getDateObtained(), titulation1.getInstructorName(), titulation1.getClubId());
        Mockito.when(mockPassportClient.addTitulation(user1.getId(),titulationDTO))
                .thenReturn(titulation1);
        String body = objectMapper.writeValueAsString(titulationDTO);

        MvcResult mvcResult = mockMvc.perform(post("/add-titulation/passport/"+user1.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1*"));

        Mockito.verify(mockPassportClient).addTitulation(user1.getId(),titulationDTO);
    }

    @Test
    void modifyTitulation() throws Exception {
        TitulationDTO titulationDTO = new TitulationDTO(titulation1.getOrganization(),"2**",
                titulation1.getDateObtained(), titulation1.getInstructorName(), titulation1.getClubId());

        Mockito.doAnswer(inputs -> {
            titulation1.setTitleName(titulationDTO.getTitleName());
            return null;
        }).when(mockPassportClient).modifyTitulation(titulation1.getId(), titulationDTO);

        String body = objectMapper.writeValueAsString(titulationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/modify-titulation/"+titulation1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();

        assertTrue(titulation1.getTitleName().equals("2**"));

        Mockito.verify(mockPassportClient).modifyTitulation(titulation1.getId(), titulationDTO);
    }

    @Test
    void getAllClubs() throws Exception {
        Mockito.when(mockClubClient.getAllClubs())
                .thenReturn(List.of(club1,club2));

        MvcResult mvcResult = mockMvc.perform(get("/clubs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BlauMar"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Hesperides"));

        Mockito.verify(mockClubClient).getAllClubs();
    }

    @Test
    void getClub() throws Exception {
        Mockito.when(mockClubClient.getClub(club1.getId()))
                .thenReturn(club1);

        MvcResult mvcResult = mockMvc.perform(get("/clubs/"+club1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BlauMar"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Hesperides"));

        Mockito.verify(mockClubClient).getClub(club1.getId());
    }

    @Test
    void createClub() throws Exception {
        Mockito.when(mockClubClient.createClub(club1))
                .thenReturn(club1);
        String body = objectMapper.writeValueAsString(club1);

        MvcResult mvcResult = mockMvc.perform(post("/clubs")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BlauMar"));

        Mockito.verify(mockClubClient).createClub(club1);
    }

    @Test
    void modifyClub() throws Exception {
        Club club = new Club("Holi", club1.getStreet(),club1.getHomeNumber(),club1.getCity(),club1.getPostalCode(),
                club1.getContactPhone(),club1.getCountry(),club1.getEmail(),club1.getRating());

        Mockito.doAnswer(inputs -> {
            club1.setName(club.getName());
            return null;
        }).when(mockClubClient).modifyClub(club1.getId(), club);

        String body = objectMapper.writeValueAsString(club);

        MvcResult mvcResult = mockMvc.perform(put("/clubs/"+club1.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn(); //Para cerrar la petición

        assertTrue(club1.getName().equals(club.getName()));

        Mockito.verify(mockClubClient).modifyClub(club1.getId(), club);
    }

    @Test
    void deleteClub() throws Exception {

        Mockito.doNothing().when(mockClubClient).deleteClub(club1.getId());

        MvcResult mvcResult = mockMvc.perform(delete("/clubs/"+club1.getId())
                ).andExpect(status().isNoContent())
                .andReturn(); //Para cerrar la petición

        Mockito.verify(mockClubClient).deleteClub(club1.getId());
    }
}