package com.example.personal_movie_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class PersonalMovieTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalMovieTrackerApplication.class, args);
	}

}
