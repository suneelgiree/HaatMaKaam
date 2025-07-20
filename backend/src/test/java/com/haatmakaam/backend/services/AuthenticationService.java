package com.haatmakaam.backend.services;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.domain.enums.UserRole;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.models.LoginRequest;
import com.haatmakaam.backend.models.LoginResponse;
import com.haatmakaam.backend.models.OtpVerificationRequest; // Import the new request model
import com.haatmakaam.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        // Using your UserRole enum
    public User register(RegisterRequest request) {
        User user = new User();
        user.setFullName(request.getName()); // Use 'fullName'
        user.setPhoneNumber(request.getPhone()); // Use 'phoneNumber'
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.valueOf(request.getRole().toUpperCase())); // Convert string to enum
        user.setVerified(false);

        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        otpService.sendOtp(savedUser.getPhoneNumber(), otp); // Use getPhoneNumber()
        return savedUser;
    }

    public boolean verifyOtp(OtpVerificationRequest request) {
        // Use the new repository method
        User user = userRepository.findByPhoneNumber(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + request.getPhone()));
        // ... (rest of the logic is the same)
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );
        // Use the new repository method
        User user = userRepository.findByPhoneNumber(request.getPhone())
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // No need to check isVerified() here, because user.isEnabled() in UserDetails does it for us.
        
        String jwt = jwtService.generateToken(user);
        return new LoginResponse(jwt);
    }
}
}