package com.ironhack.divingpassportservice.repository;

import com.ironhack.divingpassportservice.model.Titulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitulationRepository extends JpaRepository<Titulation, Long> {
}
