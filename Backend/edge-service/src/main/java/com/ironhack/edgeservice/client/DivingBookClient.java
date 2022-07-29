package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.edgeservice.controller.dto.DiveDTO;
import com.ironhack.edgeservice.model.Dive;
import com.ironhack.edgeservice.model.DiveBook;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient("diving-book-service")
public interface DivingBookClient {

    @GetMapping("/dive-book/{userId}")
    DiveBook getDiveBook(@PathVariable Long userId);

    @PostMapping("/create/dive-book")
    DiveBook createDiveBook(@RequestBody DiveBook diveBook);

    @PostMapping("/add-dive/dive-book/{userId}")
    Dive addDiveToDiveBook(@PathVariable Long userId, @RequestBody DiveDTO diveDTO);

    @PutMapping("/modify-dive/{id}")
    void modifyDiveToDiveBook(@PathVariable Long id, @RequestBody DiveDTO diveDTO);

    @PutMapping("/dive/{id}/change-confirmation")
    void changeConfirmation(@PathVariable Long id, @RequestBody DiveConfirmationDTO diveConfirmationDTO);

    @DeleteMapping("/delete/dive-book/{userId}")
    void deleteDiveBook(@PathVariable Long userId);
}
