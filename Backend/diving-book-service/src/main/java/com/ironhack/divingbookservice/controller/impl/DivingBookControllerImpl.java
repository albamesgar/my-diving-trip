package com.ironhack.divingbookservice.controller.impl;

import com.ironhack.divingbookservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.divingbookservice.controller.dto.DiveDTO;
import com.ironhack.divingbookservice.controller.interfaces.DivingBookController;
import com.ironhack.divingbookservice.model.Dive;
import com.ironhack.divingbookservice.model.DiveBook;
import com.ironhack.divingbookservice.repository.DiveBookRepository;
import com.ironhack.divingbookservice.repository.DiveRepository;
import com.ironhack.divingbookservice.service.interfaces.DivingBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@RestController
public class DivingBookControllerImpl implements DivingBookController {
    @Autowired
    private DiveBookRepository diveBookRepository;
    @Autowired
    private DivingBookService divingBookService;

    @GetMapping("/dive-book/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DiveBook getDiveBook(@PathVariable Long userId){
        return divingBookService.getDiveBook(userId);
    }

    @PostMapping("/create/dive-book")
    @ResponseStatus(HttpStatus.CREATED)
    public DiveBook createDiveBook(@RequestBody DiveBook diveBook) {
        return diveBookRepository.save(diveBook);
    }

    @PostMapping("/add-dive/dive-book/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dive addDiveToDiveBook(@PathVariable Long userId, @RequestBody DiveDTO diveDTO) {
        return divingBookService.addDiveToDiveBook(userId,diveDTO);
    }

    @PutMapping("/modify-dive/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyDiveToDiveBook(@PathVariable Long id, @RequestBody DiveDTO diveDTO) {
        divingBookService.modifyDiveToDiveBook(id, diveDTO);
    }

    @PutMapping("/dive/{id}/change-confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeConfirmation(@PathVariable Long id, @RequestBody DiveConfirmationDTO diveConfirmationDTO){
        divingBookService.changeConfirmation(id,diveConfirmationDTO);
    }

    @DeleteMapping("/delete/dive-book/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiveBook(@PathVariable Long userId) {
        divingBookService.deleteDiveBook(userId);
    }
}
