package com.haatmakaam.backend.config;

import com.haatmakaam.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this as a Spring configuration class.
@EnableWebSecurity // Enables Spring Security's web security support.
@RequiredArgsConstructor // Lombok: Creates a constructor for all final fields.
public class SecurityConfig {

    private final UserRepository userRepository;

    /**
     * This is the main security filter chain that acts as a firewall for our API.
     * It configures which endpoints are public and which are protected.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection, common for stateless REST APIs.
                .authorizeHttpRequests(auth -> auth
                        // Define public endpoints that do not require authentication.
                        .requestMatchers("/api/auth/**").permitAll()
                        // Any other request must be authenticated.
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // We use stateless sessions; the client will send a token with each request.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider()); // Set our custom authentication provider.

        return http.build();
    }

    /**
     * Defines the component responsible for encoding (hashing) passwords.
     * We use BCrypt, the industry standard.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This bean is the data access object for authentication.
     * It tells Spring Security how to find users and what password encoder to use.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * The AuthenticationManager is used to process an authentication request.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * This bean defines how to fetch a user from the database.
     * It uses our UserRepository to find a user by their email.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Use the new repository method to find the user by phone number
        return username -> userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone: " + username));
    }
}