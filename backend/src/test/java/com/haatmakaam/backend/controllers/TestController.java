package com.haatmakaam.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * A protected endpoint to test JWT authentication.
     * Only users with a valid JWT should be able to access this.
     * @return A welcome message including the authenticated user's phone number.
     */
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        // Get the current authentication object from the security context.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Extract the username (which is the phone number in our case).
        String currentPrincipalName = authentication.getName();
        
        // Return a personalized welcome message.
        return ResponseEntity.ok("Hello, " + currentPrincipalName + "! Your token is valid and you are authenticated.");
    }
}