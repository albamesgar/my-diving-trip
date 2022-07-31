package com.ironhack.divingpassportservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.divingpassportservice.controller.dto.TitulationDTO;
import com.ironhack.divingpassportservice.model.Passport;
import com.ironhack.divingpassportservice.model.Titulation;
import com.ironhack.divingpassportservice.repository.PassportRepository;
import com.ironhack.divingpassportservice.repository.TitulationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DivingPassportControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private TitulationRepository titulationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Passport passport1, passport2;
    Titulation titulation1, titulation2;
    @BeforeEach
    void setUp() {
        passport1 = new Passport(1L);
        passport2 = new Passport(2L);
        titulation1 = new Titulation("FEDAS","1*", Date.valueOf("2022-07-30"),
                "random",1L,passport1);
        titulation2 = new Titulation("FEDAS","2**", Date.valueOf("2022-07-30"),
                "random",1L,passport2);

        passportRepository.saveAll(List.of(passport1,passport2));
        titulationRepository.saveAll(List.of(titulation1,titulation2));
    }

    @AfterEach
    void tearDown() {
        titulationRepository.deleteAll();
        passportRepository.deleteAll();
    }

    @Test
    void getPassport() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/passport/"+passport1.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void createPassport() throws Exception {
        Passport passport = new Passport(3L);
        String body = objectMapper.writeValueAsString(passport);

        MvcResult mvcResult = mockMvc.perform(
                        post("/create/passport")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3"));
        assertTrue(passportRepository.count()==3);
    }

    @Test
    void addTitulation() throws Exception {
        TitulationDTO titulationDTO = new TitulationDTO("FEDAS","2**", Date.valueOf("2022-07-30"),
                "random",1L);
        String body = objectMapper.writeValueAsString(titulationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        post("/add-titulation/passport/"+passport1.getUserId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2**"));
        assertTrue(titulationRepository.count()==3);
        Passport passport = passportRepository.findByUserId(passport1.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(passport.getTitulations().size()==2);
    }

    @Test
    void modifyTitulation() throws Exception {
        TitulationDTO titulationDTO = new TitulationDTO("FEDAS","2**", Date.valueOf("2022-07-30"),
                "random",1L);
        String body = objectMapper.writeValueAsString(titulationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/modify-titulation/"+titulation1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();
        Titulation titulation = titulationRepository.findById(titulation1.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(titulation.getTitleName().equals("2**"));
    }

    @Test
    void deletePassport() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        delete("/delete/passport/"+passport2.getUserId())
                ).andExpect(status().isNoContent())
                .andReturn();
        assertTrue(passportRepository.count()==1);
    }
}