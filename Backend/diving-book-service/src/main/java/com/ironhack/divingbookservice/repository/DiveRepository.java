package com.ironhack.divingbookservice.repository;

import com.ironhack.divingbookservice.model.Dive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveRepository extends JpaRepository<Dive, Long> {
}
