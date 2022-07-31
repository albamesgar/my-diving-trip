package com.ironhack.divingbookservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.divingbookservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.divingbookservice.controller.dto.DiveDTO;
import com.ironhack.divingbookservice.model.Dive;
import com.ironhack.divingbookservice.model.DiveBook;
import com.ironhack.divingbookservice.repository.DiveBookRepository;
import com.ironhack.divingbookservice.repository.DiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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
class DivingBookControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DiveBookRepository diveBookRepository;
    @Autowired
    private DiveRepository diveRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Dive dive1, dive2, dive3;
    DiveBook diveBook1, diveBook2;

    @BeforeEach
    void setUp() {
        diveBook1 = new DiveBook(1L);
        diveBook2 = new DiveBook(2L);
        dive1 = new Dive(Date.valueOf("2022-07-30"),"random",22.5,55,200,
                70,19,10,"12L","Air",27,"Alba",
                "2**",1L,4,"none","none",false, diveBook1);
        dive2 = new Dive(Date.valueOf("2022-07-30"),"random",22.5,55,200,
                70,19,10,"12L","Air",27,"Alba",
                "2**",1L,4,"none","none",true, diveBook1);
        dive3 = new Dive(Date.valueOf("2022-07-30"),"random",22.5,55,200,
                70,19,10,"12L","Air",27,"Alba",
                "2**",1L,4,"none","none",true, diveBook2);

        diveBookRepository.saveAll(List.of(diveBook1,diveBook2));
        diveRepository.saveAll(List.of(dive1, dive2, dive3));
    }

    @AfterEach
    void tearDown() {
        diveRepository.deleteAll();
        diveBookRepository.deleteAll();
    }

    @Test
    void getDiveBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/dive-book/"+diveBook1.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void createDiveBook() throws Exception {
        DiveBook diveBook = new DiveBook(3L);
        String body = objectMapper.writeValueAsString(diveBook);

        MvcResult mvcResult = mockMvc.perform(
                        post("/create/dive-book")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3"));
        assertTrue(diveBookRepository.count()==3);
    }

    @Test
    void addDiveToDiveBook() throws Exception {
        DiveDTO diveDTO = new DiveDTO(Date.valueOf("2022-07-29"),"Medas",20,50,
                "Alba","2**",200,50,20,20,"12L",
                "Air",27,1L,"none","none",4,true);
        String body = objectMapper.writeValueAsString(diveDTO);

        MvcResult mvcResult = mockMvc.perform(
                        post("/add-dive/dive-book/"+diveBook1.getUserId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Medas"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("200"));
        assertTrue(diveRepository.count()==4);
        DiveBook diveBook = diveBookRepository.findByUserId(diveBook1.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(diveBook.getDives().size()==3);
    }

    @Test
    void modifyDiveToDiveBook() throws Exception {
        DiveDTO diveDTO = new DiveDTO(Date.valueOf("2022-07-29"),"Medas",20,50,
                "Alba","2**",200,50,20,20,"12L",
                "Air",27,1L,"none","none",4,true);
        String body = objectMapper.writeValueAsString(diveDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/modify-dive/"+dive1.getId())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();
        Dive dive = diveRepository.findById(dive1.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(dive.getLocation().equals("Medas"));
    }

    @Test
    void changeConfirmation() throws Exception {
        DiveConfirmationDTO diveConfirmationDTO = new DiveConfirmationDTO(true);
        String body = objectMapper.writeValueAsString(diveConfirmationDTO);

        MvcResult mvcResult = mockMvc.perform(
                        put("/dive/"+dive1.getId()+"/change-confirmation")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent())
                .andReturn();
        Dive dive = diveRepository.findById(dive1.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertTrue(dive.isClubValidation());
    }

    @Test
    void deleteDiveBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        delete("/delete/dive-book/"+diveBook1.getUserId())
                ).andExpect(status().isNoContent())
                .andReturn();
        assertTrue(diveBookRepository.count()==1);
    }
}