package com.example.personal_movie_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_movie_tracker.exceptions.UserNotFoundException;
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
}
