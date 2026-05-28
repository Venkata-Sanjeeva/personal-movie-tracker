package com.example.personal_movie_tracker.model;

import com.example.personal_movie_tracker.utils.IdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favourites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "movie_id"}) // Prevents favoriting the same movie twice
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(unique = true, nullable = false)
    private String favUID;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @PrePersist
    protected void onCreate() {
        if (this.favUID == null) {
            this.favUID = IdentifierGenerator.generate("fav");
        }
    }
}