package com.example.personal_movie_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.model.Favourite;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.repository.FavouriteRepository;
import com.example.personal_movie_tracker.repository.MovieRepository;

@Service
public class FavouriteService {
	
	@Autowired
	private FavouriteRepository favouriteRepo;
	
	@Autowired
	private MovieRepository movieRepo;
	
	public String createFavourite(String movieUID) {
		
		Movie movie = movieRepo.findByMovieUID(movieUID).orElseThrow(() -> new RuntimeException());
		
		Favourite fav = new Favourite();
		
		fav.setUser(movie.getUser());
		
		movie.setFavourite(fav);
		
		favouriteRepo.save(fav);
		
		return "Movie added to Favourites...";
	}
	
	public String deleteFavourite(String favUID, String movieUID) {
		
		Movie movie = movieRepo.findByMovieUID(movieUID).orElseThrow();

		movie.setFavourite(null);
		
		movieRepo.save(movie);
		
		favouriteRepo.delete(favouriteRepo.findByFavUID(favUID).orElseThrow());
		
		return "Movie deleted from favourite";
	}
}
