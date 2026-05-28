package com.example.personal_movie_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.model.Favourite;
import com.example.personal_movie_tracker.model.Movie;
import com.example.personal_movie_tracker.model.User;
import com.example.personal_movie_tracker.repository.FavouriteRepository;
import com.example.personal_movie_tracker.repository.MovieRepository;
import com.example.personal_movie_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FavouriteService {
	
    @Autowired
    private FavouriteRepository favouriteRepo;
	
    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private UserRepository userRepo; // Added to fetch the actual active user safely

    @Transactional // Ensures database consistency
    public String createFavourite(String movieUID, String userEmail) {
        // 1. Fetch the user who is performing the action
        User user = userRepo.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // 2. Fetch the movie they want to favorite
        Movie movie = movieRepo.findByMovieUID(movieUID)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        
        // 3. Optional Check: Prevent duplicating the same favorite link
        boolean alreadyExists = favouriteRepo.existsByUserAndMovie(user, movie);
        if (alreadyExists) {
            return "Movie is already in your favourites!";
        }
		
        // 4. Create the new link
        Favourite fav = new Favourite();
        fav.setUser(user);
        fav.setMovie(movie);
		
        // 5. Save the relationship
        favouriteRepo.save(fav);
		
        return "Movie added to Favourites successfully.";
    }
	
    @Transactional
    public String deleteFavourite(String favUID) {
        // With the new schema, you only need the favUID to delete the favorite entry!
        Favourite fav = favouriteRepo.findByFavUID(favUID)
            .orElseThrow(() -> new RuntimeException("Favourite record not found"));

        favouriteRepo.delete(fav);
		
        return "Movie removed from favourites successfully.";
    }
}