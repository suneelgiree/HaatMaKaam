package com.haatmakaam.backend.repositories;

import com.haatmakaam.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // Marks this as a Spring repository bean
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their email address. Spring Data JPA automatically implements this method
     * based on its name. The "Optional" return type is a modern, safe way to handle
     * cases where a user might not be found.
     *
     * @param email The email to search for.
     * @return An Optional containing the user if found, or an empty Optional otherwise.
     */
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone)

}