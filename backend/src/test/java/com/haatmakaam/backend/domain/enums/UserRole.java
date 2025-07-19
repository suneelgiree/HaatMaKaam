package com.haatmakaam.backend.domain.enums;

/**
 * Represents the roles a user can have in the system.
 * This corresponds to the 'user_role' ENUM type we created in the PostgreSQL database.
 * Using an enum ensures type safety, meaning you can't accidentally assign an invalid role.
 */
public enum UserRole {
    /**
     * A client who posts jobs and hires workers.
     */
    CLIENT,

    /**
     * A skilled laborer who registers to find and perform jobs.
     */
    WORKER,

    /**
     * An administrator who can manage users, jobs, and the platform itself.
     */
    ADMIN
}