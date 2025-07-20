package com.haatmakaam.backend.controllers;

import com.haatmakaam.backend.models.LoginRequest;
import com.haatmakaam.backend.models.LoginResponse;
import com.haatmakaam.backend.models.OtpVerificationRequest;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok("User registered successfully. Please check your phone for OTP.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationRequest request) {
        if (authenticationService.verifyOtp(request)) {
            return ResponseEntity.ok("OTP verified successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid or expired OTP.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}