package com.coders.jwt.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	
	public void setPassword(String password) {
		this.password = password;
	}
}
