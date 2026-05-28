package com.example.personal_movie_tracker.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.personal_movie_tracker.service.FavouriteService;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteController {
	
	@Autowired
	private FavouriteService favService;
	
	@PostMapping("/create/{movieUID}")
	public ResponseEntity<String> saveMovieToFavourite(
			@PathVariable String movieUID,
			Principal principal) {
		String msg = favService.createFavourite(movieUID, principal.getName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}
	
	@DeleteMapping("/delete/{favUID}")
	public ResponseEntity<String> removeMovieFromFavourite(@PathVariable String favUID) {
	    String msg = favService.deleteFavourite(favUID);
	    return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
