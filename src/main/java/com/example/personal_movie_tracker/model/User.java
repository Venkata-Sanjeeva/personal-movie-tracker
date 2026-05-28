package com.example.personal_movie_tracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.personal_movie_tracker.enums.Roles;
import com.example.personal_movie_tracker.utils.IdentifierGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userUID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    // One user can log many movies
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Movie> movies = new HashSet<>();
    
    // One user can have many favorited items
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favourite> favourites = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.userUID == null) {
            this.userUID = IdentifierGenerator.generate("USER");
        }
    }
}