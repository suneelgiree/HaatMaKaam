package com.haatmakaam.backend.services;

import com.haatmakaam.backend.domain.entities.User;
import com.haatmakaam.backend.models.RegisterRequest;
import com.haatmakaam.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;
import java.text.DecimalFormat;

/**
 * Service to handle user authentication, including registration and future login/verification logic.
 */
@Service
public class AuthenticationService {

    // Dependency on the repository to interact with the User data in the database.
    private final UserRepository userRepository;
    // Dependency on the password encoder to securely hash passwords.
    private final PasswordEncoder passwordEncoder;
    // Dependency on the OtpService to send SMS messages.
    private final OtpService otpService;

    /**
     * Constructor-based dependency injection. Spring will provide instances of these beans.
     * @param userRepository Repository for user data access.
     * @param passwordEncoder Bean for hashing passwords.
     * @param otpService Service for sending OTPs.
     */
    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    /**
     * Handles the registration of a new user.
     * @param request The request object containing the new user's details.
     * @return The User entity that was saved to the database.
     */
    public User register(RegisterRequest request) {
        // TODO: Add validation to check if a user with the given phone number already exists.

        // Create a new User entity and populate it with data from the request.
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        // Always encode the password before saving it to the database for security.
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        // A new user is not verified by default.
        user.setVerified(false);

        // --- OTP Logic ---
        // 1. Generate a random 6-digit OTP.
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));

        // 2. Set the generated OTP and the current timestamp on the user entity.
        // This is necessary to verify the OTP and check if it has expired later.
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());

        // 3. Save the new user record to the database.
        User savedUser = userRepository.save(user);

        // 4. After successfully saving the user, send the OTP to their phone.
        // This ensures we don't send an SMS if there's a database issue.
        otpService.sendOtp(savedUser.getPhone(), otp);

        // Return the saved user entity. The controller can then decide what response to send to the client.
        return savedUser;
    }
}