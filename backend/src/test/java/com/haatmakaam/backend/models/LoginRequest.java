package com.haatmakaam.backend.models;

/**
 * Model for the user login request payload.
 */
public class LoginRequest {
    private String phone;
    private String password;

    // Getters and Setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}