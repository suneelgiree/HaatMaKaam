package com.haatmakaam.backend.domain.entities;

import com.haatmakaam.backend.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// --- LOMBOK ANNOTATIONS ---
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// --- JPA ANNOTATIONS ---
@Entity
@Table(name = "users")
// This is a database-level constraint to ensure data integrity.
// It guarantees that a user record cannot be created unless it has either an email or a phone number.
@Check(constraints = "email IS NOT NULL OR phone_number IS NOT NULL")
public class User implements UserDetails {

    // --- FIELDS AND COLUMN MAPPINGS ---

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    // Email is now nullable, but must remain unique if it is provided.
    @Column(nullable = true, unique = true)
    private String email;

    // We will use this for password-based login. It can be null for OTP/social logins.
    @Column(nullable = true)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Phone number is also unique if provided.
    @Column(unique = true)
    private String phoneNumber;

    private String profilePictureUrl;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // --- SPRING SECURITY METHODS (UserDetails Interface) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        // Spring Security's "username" can be any unique identifier.
        // We will prioritize email, but could adapt this logic later.
        // For now, this remains the primary identifier for password-based auth.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}