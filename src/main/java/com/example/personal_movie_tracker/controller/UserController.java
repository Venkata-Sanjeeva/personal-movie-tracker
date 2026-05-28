package com.example.personal_movie_tracker.controller;

import com.example.personal_movie_tracker.exceptions.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_movie_tracker.exceptions.UserNotFoundException;
import com.example.personal_movie_tracker.requests.CreateMovieRequest;
import com.example.personal_movie_tracker.responses.UserMoviesResponse;
import com.example.personal_movie_tracker.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/movies")
	public ResponseEntity<?> getUserCreatedMovies(Authentication auth) {
		try {
			UserMoviesResponse response = userService.fetchUserCreatedMovies(auth.getName());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (UserNotFoundException notFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping("/movies/create")
	public ResponseEntity<?> createMovieForUser(@RequestBody CreateMovieRequest request, Authentication auth) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createMovieForUser(request, auth.getName()));
		} catch (UserNotFoundException notFoundExcept) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundExcept.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PatchMapping("/movies/{movieUID}/status/{statusType}")
	public ResponseEntity<?> updateMovieStatus(@PathVariable String statusType, @PathVariable String movieUID, Authentication auth) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.updateMovieStatus(movieUID, auth.getName(), statusType));
		} catch (MovieNotFoundException notFoundExcept) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundExcept.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
