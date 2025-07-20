package com.haatmakaam.backend.services;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.models.OtpVerificationRequest; // Import the new request model
import com.haatmakaam.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;
import java.text.DecimalFormat;

@Service
public class AuthenticationService {

    // Define OTP expiration time in minutes. 10 minutes is a reasonable duration.
    private static final long OTP_VALIDITY_MINUTES = 10;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    public User register(RegisterRequest request) {
        // ... (your existing register method)
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setVerified(false);
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        otpService.sendOtp(savedUser.getPhone(), otp);
        return savedUser;
    }

    /**
     * Verifies a user's phone number using the provided OTP.
     * @param request The request containing the user's phone and the OTP.
     * @return true if verification is successful, false otherwise.
     */
    public boolean verifyOtp(OtpVerificationRequest request) {
        // 1. Find the user by their phone number.
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + request.getPhone()));

        // 2. Check if the user is already verified.
        if (user.isVerified()) {
            // Or handle as you see fit, maybe return a specific message.
            return true; 
        }

        // 3. Check if the OTP is correct and not expired.
        if (user.getOtp().equals(request.getOtp()) && 
            user.getOtpGeneratedTime().plusMinutes(OTP_VALIDITY_MINUTES).isAfter(LocalDateTime.now())) {
            
            // 4. If verification is successful, update the user's status.
            user.setVerified(true);
            // It's good practice to clear the OTP fields after successful verification.
            user.setOtp(null);
            user.setOtpGeneratedTime(null);
            userRepository.save(user);
            return true;
        }

        // 5. If OTP is incorrect or expired, return false.
        return false;
    }
}