package com.haatmakaam.backend.services;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service contains the core business logic for user authentication,
 * starting with user registration.
 */
@Service // Marks this class as a Spring service bean, making it available for dependency injection.
@RequiredArgsConstructor // Lombok: Creates a constructor for all final fields.
public class AuthenticationService {

    // --- DEPENDENCIES ---
    // These are the components this service needs to function.
    // Spring will automatically inject them thanks to @RequiredArgsConstructor.

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * @param request The RegisterRequest DTO containing the new user's information.
     * @return The newly created User entity.
     */
    public User register(RegisterRequest request) {
        // Here, you could add validation logic, e.g., check if email already exists.
        // For now, the database's unique constraint on the email column handles this.

        // 1. Create a new User object using the Builder pattern.
        // This is a safe and readable way to construct objects.
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                // 2. IMPORTANT: Hash the plain-text password from the request before saving.
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()) // Set the role from the request.
                .phoneNumber(request.getPhoneNumber())
                .build(); // Finalize the object creation.

        // 3. Save the newly created user object to the database via the repository.
        // The save() method returns the saved entity, which now includes the generated ID and timestamps.
        return userRepository.save(user);
    }
}