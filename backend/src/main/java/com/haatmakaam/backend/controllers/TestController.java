package com.haatmakaam.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public ResponseEntity<String> getProtectedData() {
        // This endpoint is secured by our SecurityConfig
        // Only users with a valid JWT can access it
        return ResponseEntity.ok("Hello, this is a protected endpoint!");
    }
}