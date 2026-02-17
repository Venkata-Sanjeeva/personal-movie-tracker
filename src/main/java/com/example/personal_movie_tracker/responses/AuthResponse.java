package com.example.personal_movie_tracker.responses;

import com.example.personal_movie_tracker.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private Roles role;
}

