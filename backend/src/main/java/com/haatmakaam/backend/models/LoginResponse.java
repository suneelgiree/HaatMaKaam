package com.haatmakaam.backend.models;

/**
 * Model for the successful login response, containing the JWT.
 */
public record LoginResponse(String token) {
}