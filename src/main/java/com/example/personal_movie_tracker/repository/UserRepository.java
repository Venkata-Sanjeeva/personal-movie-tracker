package com.example.personal_movie_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_movie_tracker.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
    Optional<User> findByUserUID(String userUID);
    boolean existsByEmail(String email);
}
