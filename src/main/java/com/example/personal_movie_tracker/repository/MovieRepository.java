package com.example.personal_movie_tracker.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.personal_movie_tracker.enums.MovieStatus;
import com.example.personal_movie_tracker.model.Movie;

import jakarta.transaction.Transactional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 1. Find a specific movie for a specific user (Security Best Practice)
    Optional<Movie> findByMovieUIDAndUser_UserUID(String movieUID, String userUID);

    // 2. Get all movies for a specific user by their UserUID
    Set<Movie> findAllByUser_UserUID(String userUID);

    // 3. Find movies by status for a specific user (e.g., all "WATCHED" movies)
    Set<Movie> findByStatusAndUser_UserUID(MovieStatus status, String userUID);
    
    // 4. Find the most recently added movies for a user
    List<Movie> findByUser_UserUIDOrderByCreatedAtDesc(String userUID);

    // 5. Search movies by title (Case-Insensitive) for a specific user
    Set<Movie> findByTitleContainingIgnoreCaseAndUser_UserUID(String title, String userUID);

    // 6. Count how many movies a user has in a specific status
    long countByStatusAndUser_UserUID(MovieStatus status, String userUID);
    
    // 7. Delete a specific movie by UID and User (returns number of deleted records)
    void deleteByMovieUIDAndUser_UserUID(String movieUID, String userUID);
    
    // 8. Check if a movie exists for a user before attempting operations
    boolean existsByMovieUIDAndUser_UserUID(String movieUID, String userUID);
    
    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.status = :newStatus WHERE m.user.userUID = :userUID AND m.movieUID = :movieUID")
    void updateStatusForUser(String movieUID, MovieStatus newStatus, String userUID);
    
    
}