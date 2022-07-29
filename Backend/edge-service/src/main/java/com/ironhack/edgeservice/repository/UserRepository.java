package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameAndPassword(String name, String password);
    Optional<User> findByUsername(String username);

    @Query("select u.username from User u")
    List<String> findUsernames();
}
