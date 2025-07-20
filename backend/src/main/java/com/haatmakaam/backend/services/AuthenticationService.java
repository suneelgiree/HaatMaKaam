package com.haatmakaam.backend.services;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.domain.enums.UserRole;
import com.haatmakaam.backend.exceptions.UserNotFoundException;
import com.haatmakaam.backend.models.LoginRequest;
import com.haatmakaam.backend.models.LoginResponse;
import com.haatmakaam.backend.models.OtpVerificationRequest;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            OtpService otpService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {
        // Optional: Check if user already exists
        userRepository.findByPhoneNumber(request.phone()).ifPresent(u -> {
            throw new IllegalArgumentException("User with phone number " + request.phone() + " already exists.");
        });

        User user = new User();
        user.setFullName(request.name());
        user.setPhoneNumber(request.phone());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.valueOf(request.role().toUpperCase()));
        user.setVerified(false);

        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        otpService.sendOtp(savedUser.getPhoneNumber(), otp);
        return savedUser;
    }

    public boolean verifyOtp(OtpVerificationRequest request) {
        User user = userRepository.findByPhoneNumber(request.phone())
                .orElseThrow(() -> new UserNotFoundException("User not found with phone: " + request.phone()));

        if (user.getOtp().equals(request.otp()) &&
            user.getOtpGeneratedTime().plusMinutes(10).isAfter(LocalDateTime.now())) {
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        // Throw a specific error for invalid OTP
        throw new IllegalArgumentException("The OTP provided is invalid or has expired.");
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.phone(), request.password())
        );
        User user = userRepository.findByPhoneNumber(request.phone())
            .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with phone: " + request.phone()));

        if (!user.isVerified()) {
            throw new IllegalArgumentException("User is not verified. Please verify your OTP first.");
        }

        String jwt = jwtService.generateToken(user);
        return new LoginResponse(jwt);
    }
}