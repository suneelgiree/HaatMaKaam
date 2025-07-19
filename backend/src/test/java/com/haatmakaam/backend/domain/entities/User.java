package com.haatmakaam.backend.domain.entities;

import com.haatmakaam.backend.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
// These annotations from Project Lombok help us write less boilerplate code.

@Data // Generates all the standard methods: getters for all fields, setters for all non-final fields, and appropriate toString, equals, and hashCode implementations.
@NoArgsConstructor // Generates a constructor with no arguments. This is required by JPA.
@AllArgsConstructor // Generates a constructor with one argument for every field in the class.
@Builder // Implements the "Builder" pattern, which is a clean way to create instances of this class.

// --- JPA ANNOTATIONS ---
// These annotations from the Jakarta Persistence API (JPA) tell Spring how to treat this class.

@Entity // Marks this Java class as a database entity, meaning it will be managed by JPA.
@Table(name = "users") // Specifies that this entity maps to the table named "users" in our database.
public class User implements UserDetails {

    // --- FIELDS AND COLUMN MAPPINGS ---

    @Id // Marks this field as the primary key for the 'users' table.
    @GeneratedValue(strategy = GenerationType.UUID) // Configures the primary key generation strategy. We use UUIDs, which are great for distributed systems and security as they are not guessable.
    private UUID id;

    @Column(nullable = false) // Maps this field to a column in the 'users' table. `nullable = false` means this column must have a value; it cannot be null.
    private String fullName;

    @Column(nullable = false, unique = true) // The `unique = true` constraint ensures that no two users can have the same email address in the database.
    private String email;

    @Column(nullable = false)
    private String passwordHash; // We name it 'passwordHash' to be clear that we are NEVER storing plain-text passwords.

    @Enumerated(EnumType.STRING) // Specifies how the enum is stored in the database. `EnumType.STRING` means it will store the role as a string (e.g., "CLIENT", "WORKER").
    @Column(nullable = false)
    private UserRole role;

    @Column(unique = true) // Phone number should also be unique if provided. It's not `nullable = false` because a user might register with email first.
    private String phoneNumber;

    private String profilePictureUrl; // This field can be null.

    @CreationTimestamp // This annotation from Hibernate automatically sets this field to the current timestamp when a new User is first saved to the database.
    private Instant createdAt; // We use `Instant` as it's the modern Java standard for representing a point in time, typically in UTC.

    @UpdateTimestamp // This annotation from Hibernate automatically updates this field to the current timestamp every time the User entity is modified.
    private Instant updatedAt;

    // --- SPRING SECURITY METHODS (UserDetails Interface) ---
    // These methods are required by Spring Security to handle authentication and authorization.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This method provides the user's role to Spring Security.
        // We wrap our UserRole in a SimpleGrantedAuthority object.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        // Spring Security will call this method to get the hashed password for comparison.
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        // We are using the email address as the unique identifier for login.
        return this.email;
    }

    // The following methods can be used to disable accounts, lock them, etc.
    // For now, we will return `true` to indicate that accounts are always active.
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