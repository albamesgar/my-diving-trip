package com.ironhack.divingclubsservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.divingclubsservice.model.Club;
import com.ironhack.divingclubsservice.repository.ClubRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DivingClubsControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClubRepository clubRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Club club1, club2;

    @BeforeEach
    void setUp() {
        club1 = new Club("BlauMar", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        club2 = new Club("Hesperides", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        clubRepository.saveAll(List.of(club1,club2));
    }

    @AfterEach
    void tearDown() {
        clubRepository.deleteAll();
    }

    @Test
    void getAllClubs() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/clubs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BlauMar"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Hesperides"));
    }

    @Test
    void getClub() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/clubs/"+club1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BlauMar"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Hesperides"));
    }

    @Test
    void createClub() throws Exception {
        Club club = new Club("Random", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        String body = objectMapper.writeValueAsString(club);

        MvcResult mvcResult = mockMvc.perform(
                        post("/create/club")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Random"));
        assertTrue(clubRepository.count()==3);
    }

    @Test
    void modifyClub() throws Exception {
        Club club = new Club("Random", "street", 1, "city", 1, 60660L,
                "country", "email", 3.5);
        String body = objectMapper.writeValueAsString(club);

        MvcResult mvcResult = mockMvc.perform(
                        put("/modify-club/"+club1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();
        Club club3 = clubRepository.findById(club1.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(club3.getName().equals("Random"));
    }

    @Test
    void deleteClub() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        delete("/delete/club/"+club1.getId())
                ).andExpect(status().isNoContent())
                .andReturn();
        assertTrue(clubRepository.count()==1);
    }
}