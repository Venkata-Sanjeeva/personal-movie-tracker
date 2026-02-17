package com.example.personal_movie_tracker.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieRequest {
	private String title;
	private Integer releaseYear;
}
