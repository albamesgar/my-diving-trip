package com.ironhack.divingclubsservice.repository;

import com.ironhack.divingclubsservice.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findClubByName(String name);
}
