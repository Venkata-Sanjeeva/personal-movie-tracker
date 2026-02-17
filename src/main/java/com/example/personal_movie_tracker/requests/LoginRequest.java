package com.example.personal_movie_tracker.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
