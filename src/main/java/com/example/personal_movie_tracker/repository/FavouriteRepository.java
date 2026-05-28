package com.example.personal_movie_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_movie_tracker.model.Favourite;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.model.User;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
	Optional<Favourite> findByFavUID(String favUID);
	
	Boolean existsByUserAndMovie(User user, Movie movie);
	
	Favourite findByUserAndMovie(User user, Movie movie);
}
