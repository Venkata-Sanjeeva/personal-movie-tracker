package com.example.personal_movie_tracker.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.exceptions.MovieNotFoundException;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
	
	private final MovieRepository movieRepo;
	
	public Movie fetchMovieByUID(String userUID, String movieUID) {
		return movieRepo.findByMovieUIDAndUser_UserUID(movieUID, userUID)
				.orElseThrow(() -> new MovieNotFoundException("Movie with ID: " + movieUID + " not found!"));
	}
	
	public Set<Movie> fetchMoviesByUserId(String userUID) {
		return movieRepo.findAllByUser_UserUID(userUID);
	}

}
