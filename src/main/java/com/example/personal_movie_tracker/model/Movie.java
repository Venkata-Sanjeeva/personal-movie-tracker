package com.example.personal_movie_tracker.model;

import java.time.LocalDateTime;

import com.example.personal_movie_tracker.enums.MovieStatus;
import com.example.personal_movie_tracker.utils.IdentifierGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Movie {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(nullable = false)
    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MovieStatus status; 

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_watched_at")
    private LocalDateTime lastWatchedAt;
    
    @Column(name = "movieUID", nullable = false)
    private String movieUID;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = MovieStatus.WANT_TO_DOWNLOAD;
        }
        if(this.movieUID == null) {
        	this.movieUID = IdentifierGenerator.generate("MOVIE");
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (this.status == MovieStatus.WATCHING || this.status == MovieStatus.WATCHED) {
            this.lastWatchedAt = LocalDateTime.now();
        }
    }
}