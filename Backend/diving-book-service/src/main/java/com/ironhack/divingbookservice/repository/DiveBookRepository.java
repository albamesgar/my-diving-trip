package com.ironhack.divingbookservice.repository;

import com.ironhack.divingbookservice.model.DiveBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiveBookRepository extends JpaRepository<DiveBook, Long> {
    Optional<DiveBook> findByUserId(Long userId);
}
