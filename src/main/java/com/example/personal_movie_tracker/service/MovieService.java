package com.example.personal_movie_tracker.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.enums.MovieStatus;
import com.example.personal_movie_tracker.exceptions.MovieNotFoundException;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
	
	private final MovieRepository movieRepo;
	
	// 1. Get specific movie for a specific user
	public Movie fetchMovieByuserUIDandMovieUID(String userUID, String movieUID) {
		return movieRepo.findByMovieUIDAndUser_UserUID(movieUID, userUID)
				.orElseThrow(() -> new MovieNotFoundException("Movie with ID: " + movieUID + " not found!"));
	}
	
	// 2. Get all movies for a specific user
	public Set<Movie> fetchMoviesByUserUID(String userUID) {
		return movieRepo.findAllByUser_UserUID(userUID);
	}
	
	// 3. Get movies by status for a specific user
	public Set<Movie> fetchMoviesByStatusandUserUID(MovieStatus status, String userUID) {
		return movieRepo.findByStatusAndUser_UserUID(status, userUID);
	}

	// 4. Get the most recently added movies for a user
	public List<Movie> fetchRecentlyAddedMoviesOfaUser(String userUID) {
		return movieRepo.findByUser_UserUIDOrderByCreatedAtDesc(userUID);
	}
	
	// 5. Search movies by title (Case-Insensitive) for a specific user
	public Set<Movie> getMoviesBySearchOfaUser(String title, String userUID) {
		return movieRepo.findByTitleContainingIgnoreCaseAndUser_UserUID(title, userUID);
	}
	
	// 6. Count how many movies a user has in a specific status
	public long countMoviesByStatusByUserUID(MovieStatus status, String userUID) {
		return movieRepo.countByStatusAndUser_UserUID(status, userUID);
	}
	
	// 7. Check if a movie exists for a user
	public boolean existsByMovieUIDandUserUID(String movieUID, String userUID) {
		return movieRepo.existsByMovieUIDAndUser_UserUID(movieUID, userUID);
	}
	
	// 8. Delete a specific movie by movie UID and User UID
	public void deleteMovieByUIDandUserUID(String movieUID, String userUID) throws MovieNotFoundException {
		if(existsByMovieUIDandUserUID(movieUID, userUID)) {
			movieRepo.deleteByMovieUIDAndUser_UserUID(movieUID, userUID);
		}
		throw new MovieNotFoundException("Movie with ID: " + movieUID + " for user with ID: " + userUID + " not found!");
	}
	
	// Update status of a movie using user UID and movie UID
	public boolean updateMovieStatusByMovieUIDandUserUID(MovieStatus status, String movieUID, String userUID) {
		if(existsByMovieUIDandUserUID(movieUID, userUID)) {
			movieRepo.updateStatusForUser(movieUID, status, userUID);
			return true;
		} 
		
		throw new MovieNotFoundException("Movie with ID: " + movieUID + " for user with ID: " + userUID + " not found!");
	}
	
	public List<Movie> fetchAllAvailableMovies() {
		return movieRepo.findAll();
	}
	
}
