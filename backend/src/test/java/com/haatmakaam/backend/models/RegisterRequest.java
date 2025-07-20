package com.haatmakaam.backend.models;

import com.haatmakaam.backend.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object (DTO) that represents the payload for a user registration request.
 * It contains the necessary information for creating a new user.
 */
@Data // Lombok: Generates getters, setters, toString(), etc.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // We can add validation annotations here later (e.g., @NotBlank, @Email)
    private String fullName;
    private String email;
    private String password;
    private UserRole role; // e.g., "CLIENT" or "WORKER"
    private String phoneNumber;
}