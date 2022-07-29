package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.controller.dto.TitulationDTO;
import com.ironhack.edgeservice.model.Passport;
import com.ironhack.edgeservice.model.Titulation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient("diving-passport-service")
public interface DivingPassportClient {

    @GetMapping("/passport/{userId}")
    Passport getPassport(@PathVariable Long userId);

    @PostMapping("/create/passport")
    Passport createPassport(@RequestBody Passport passport);

    @PostMapping("/add-titulation/passport/{userId}")
    Titulation addTitulation(@PathVariable Long userId, @RequestBody TitulationDTO titulationDTO);

    @PutMapping("/modify-titulation/{id}")
    void modifyTitulation(@PathVariable Long id, @RequestBody TitulationDTO titulationDTO);

    @DeleteMapping("/delete/passport/{userId}")
    void deletePassport(@PathVariable Long userId);
}
