package com.ironhack.divingclubsservice.service.interfaces;

import com.ironhack.divingclubsservice.model.Club;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DivingClubsService {
    Club getClub(Long id);
    void modifyClub(Long id, Club club);
    void deleteClub(Long id);
}
