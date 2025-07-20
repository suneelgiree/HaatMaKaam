package com.haatmakaam.backend.models;

// This is a record, so it does not need Lombok annotations.
public record RegisterRequest(
    String name,
    String phone,
    String password,
    String role
) {
}