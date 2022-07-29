package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.model.Club;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("diving-clubs-service")
public interface DivingClubClient {
    @GetMapping("/clubs")
    List<Club> getAllClubs();

    @GetMapping("/clubs/{id}")
    Club getClub(@PathVariable Long id);

//    @GetMapping("/clubs/{name}")
//    Club getClubByName(@PathVariable String name);

    @PostMapping("/create/club")
    Club createClub(@RequestBody Club club);

    @PutMapping("/modify-club/{id}")
    void modifyClub(@PathVariable Long id, @RequestBody Club club);

    @DeleteMapping("/delete/club/{id}")
    void deleteClub(@PathVariable Long id);
}
