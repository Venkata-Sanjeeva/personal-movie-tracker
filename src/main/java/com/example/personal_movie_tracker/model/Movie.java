package com.example.personal_movie_tracker.model;

import java.time.LocalDateTime;

import com.example.personal_movie_tracker.enums.MovieStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder // Useful for creating movie objects in tests/services
public class Movie {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(nullable = false)
    private String title;

    @Column(length = 1000) // Allows for longer descriptions
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Enumerated(EnumType.STRING) // Saves "WATCHED" instead of 3
    @Column(name = "status", nullable = false)
    private MovieStatus status; 

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_watched_at")
    private LocalDateTime lastWatchedAt;

    // --- Lifecycle Hooks ---

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = MovieStatus.WANT_TO_DOWNLOAD;
        }
    }

    // Optional: Update lastWatchedAt automatically if status changes to WATCHED
    @PreUpdate
    protected void onUpdate() {
        if (this.status == MovieStatus.WATCHING || this.status == MovieStatus.WATCHED) {
            this.lastWatchedAt = LocalDateTime.now();
        }
    }
}
