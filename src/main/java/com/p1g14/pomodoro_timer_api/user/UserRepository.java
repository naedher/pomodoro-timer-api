package com.p1g14.pomodoro_timer_api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 * Provides CRUD operations and query method support.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     * @param email the email to search by
     * @return an optional containing the user, if found
     */
    Optional<User> findByEmail(String email);
}
