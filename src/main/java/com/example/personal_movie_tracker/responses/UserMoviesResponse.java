package com.example.personal_movie_tracker.responses;

import java.util.Set;

import com.example.personal_movie_tracker.model.Movie;

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
	private Set<Movie> moviesList;
}
