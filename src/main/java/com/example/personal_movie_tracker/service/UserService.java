package com.example.personal_movie_tracker.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.exceptions.UserNotFoundException;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.model.User;
import com.example.personal_movie_tracker.repository.UserRepository;
import com.example.personal_movie_tracker.responses.UserMoviesResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepo;
	private final MovieService movieService;
	
	public User fetchUserByEmailID(String userEmailId) throws UserNotFoundException {
		return userRepo.findByEmail(userEmailId)
				.orElseThrow(() -> new UserNotFoundException("User with email ID: " + userEmailId + " not found!"));
	}
	
	public User fetchUserByUID(String userUID) throws UserNotFoundException {
		return userRepo.findByUserUID(userUID)
				.orElseThrow(() -> new UserNotFoundException("User with Unique ID: " + userUID + " not found!"));
	}
	
	public UserMoviesResponse fetchUserCreatedMovies(String userEmailId) {
		User user = fetchUserByEmailID(userEmailId);
		
		Set<Movie> moviesList = movieService.fetchMoviesByUserId(user.getUserUID());
		
		return UserMoviesResponse.builder()
				.userUID(user.getUserUID())
				.moviesList(moviesList)
				.build();
		
	}
}
