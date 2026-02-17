package com.example.personal_movie_tracker.exceptions;

public class InvalidLoginCredentialsException extends RuntimeException {
    public InvalidLoginCredentialsException(String message) {
        super(message);
    }
}
