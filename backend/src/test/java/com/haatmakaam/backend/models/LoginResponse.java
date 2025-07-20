package com.haatmakaam.backend.models;

/**
 * Model for the successful login response, containing the JWT.
 */
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}