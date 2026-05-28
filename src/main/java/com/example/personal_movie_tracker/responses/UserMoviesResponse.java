package com.example.personal_movie_tracker.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.personal_movie_tracker.enums.MovieStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMoviesResponse {

	private String userUID;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class MovieResponse {
		private String movieUID;
		private String title;
		private Integer releaseYear;
		private MovieStatus status;
		private LocalDateTime createdAt;
		private LocalDateTime lastWatchedAt;
		private Boolean isAddedToFavourite;
		private String favUID;
	}
	
	private Set<MovieResponse> moviesList;
}
