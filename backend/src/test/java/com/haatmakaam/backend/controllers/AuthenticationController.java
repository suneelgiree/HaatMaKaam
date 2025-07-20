package com.haatmakaam.backend.controllers;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller handles all authentication-related API endpoints, such as
 * user registration and login.
 */
@RestController // Combines @Controller and @ResponseBody, marking this class for handling REST API requests.
@RequestMapping("/api/auth") // All endpoints in this controller will be prefixed with /api/auth.
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint for registering a new user.
     * It listens for POST requests on /api/auth/register.
     *
     * @param request The registration data sent in the request body.
     * @return A response entity containing the newly created user's data.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody RegisterRequest request
    ) {
        // Delegate the registration logic to the AuthenticationService
        User registeredUser = authenticationService.register(request);

        // Return the newly created user object with an HTTP status of 200 OK.
        // Later, we can return a DTO instead of the full User entity.
        return ResponseEntity.ok(registeredUser);
    }
}