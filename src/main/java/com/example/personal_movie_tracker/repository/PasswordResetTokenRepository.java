package com.example.personal_movie_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_movie_tracker.model.PasswordResetToken;
import com.example.personal_movie_tracker.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user); // Good for cleaning up old tokens
    void deleteByExpiryDateBefore(LocalDateTime now);
}