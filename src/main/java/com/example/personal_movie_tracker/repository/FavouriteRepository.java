package com.example.personal_movie_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personal_movie_tracker.model.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
	Optional<Favourite> findByFavUID(String favUID);
}
