package com.ironhack.divingbookservice.service.interfaces;

import com.ironhack.divingbookservice.controller.dto.DiveConfirmationDTO;
import com.ironhack.divingbookservice.controller.dto.DiveDTO;
import com.ironhack.divingbookservice.model.Dive;
import com.ironhack.divingbookservice.model.DiveBook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DivingBookService {
    DiveBook getDiveBook(Long userId);
    Dive addDiveToDiveBook(Long userId, DiveDTO diveDTO);
    void modifyDiveToDiveBook(Long id, DiveDTO diveDTO);
    void changeConfirmation(Long id, DiveConfirmationDTO diveConfirmationDTO);
    void deleteDiveBook(Long userId);
}
