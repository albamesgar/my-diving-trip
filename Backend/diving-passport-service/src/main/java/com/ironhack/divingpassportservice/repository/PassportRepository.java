package com.ironhack.divingpassportservice.repository;

import com.ironhack.divingpassportservice.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findByUserId(Long userId);
}
