package com.haatmakaam.backend.models;

public record LoginRequest(
    String phone,
    String password
) {
}