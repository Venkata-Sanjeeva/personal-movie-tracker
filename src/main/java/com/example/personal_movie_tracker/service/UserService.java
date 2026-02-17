package com.example.personal_movie_tracker.service;

import java.util.Set;

import com.example.personal_movie_tracker.exceptions.MovieNotFoundException;
import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.enums.MovieStatus;
import com.example.personal_movie_tracker.exceptions.UserNotFoundException;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.model.User;
import com.example.personal_movie_tracker.repository.UserRepository;
import com.example.personal_movie_tracker.requests.CreateMovieRequest;
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
		
		Set<Movie> moviesList = movieService.fetchMoviesByUserUID(user.getUserUID());
		
		return UserMoviesResponse.builder()
				.userUID(user.getUserUID())
				.moviesList(moviesList)
				.build();
	}
	
	public Movie createMovieForUser(CreateMovieRequest request, String userEmailID) throws UserNotFoundException {
		User user = fetchUserByEmailID(userEmailID);
		
		Movie movie = Movie.builder()
				.title(request.getTitle())
				.releaseYear(request.getReleaseYear())
				.user(user)
				.build();
		
		return movieService.saveMovieInDB(movie);
	}
	
	public Movie updateMovieStatus(String movieUID, String userEmailID, String status) throws MovieNotFoundException {
		User user = fetchUserByEmailID(userEmailID);
		boolean isUpdated = movieService.updateMovieStatusByMovieUIDandUserUID(mapMovieStatus(status), movieUID, user.getUserUID());
		
		if(isUpdated) {
			return movieService.fetchMovieByuserUIDandMovieUID(user.getUserUID(), movieUID);
		}
		return null;
	}
	
	public MovieStatus mapMovieStatus(String statusStr) {
		return switch (statusStr) {
			case "WANT_TO_DOWNLOAD" -> MovieStatus.WANT_TO_DOWNLOAD;
			case "DOWNLOADED" -> MovieStatus.DOWNLOADED;
			case "WATCHING" -> MovieStatus.WATCHING;
			case "WATCHED" -> MovieStatus.WATCHED;
			
			default -> 
			throw new IllegalArgumentException("Unexpected value: " + statusStr);
		};
	}
}
