package com.example.personal_movie_tracker.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	private String token;
	private String newPassword;
}
