package com.haatmakaam.backend.repositories;

import com.haatmakaam.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Find a user by their phone number
    Optional<User> findByPhoneNumber(String phoneNumber);
}
