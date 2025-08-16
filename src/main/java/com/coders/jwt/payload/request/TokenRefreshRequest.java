package com.coders.jwt.payload.request;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;

  public String getRefrashToken() {
    return refreshToken;
  }

  public void setRefrashToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
