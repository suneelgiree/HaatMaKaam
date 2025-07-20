package com.haatmakaam.backend.models;

import java.time.LocalDateTime;

public record ErrorResponse(
    String message,
    int status,
    LocalDateTime timestamp
) {
}