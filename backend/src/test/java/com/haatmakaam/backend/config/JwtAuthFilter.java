package com.haatmakaam.backend.config;

import com.haatmakaam.backend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that runs once per request to process the JWT for authentication.
 * This is the core component that makes the API stateless and token-based.
 */
@Component // Mark this as a Spring component to be managed by the container.
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * The main logic of the filter.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Step 1: Extract the Authorization header.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userPhone;

        // Step 2: Check if the header is present and starts with "Bearer ". If not, pass the request on.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Exit the filter early.
        }

        // Step 3: Extract the JWT from the header (it's the string after "Bearer ").
        jwt = authHeader.substring(7);

        // Step 4: Extract the user's phone number (the "subject") from the token.
        userPhone = jwtService.extractUsername(jwt);

        // Step 5: Check if we have a user phone and that the user is not already authenticated.
        if (userPhone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the database.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userPhone);

            // Step 6: Validate the token against the user details.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // If the token is valid, create an authentication token.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are null for token-based auth.
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Step 7: Set the authentication in the SecurityContext. This is what logs the user in for this request.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 8: Pass the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}