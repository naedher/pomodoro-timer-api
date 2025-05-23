package com.p1g14.pomodoro_timer_api.timer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Timer entities.
 * Provides CRUD operations and query method support.
 */
public interface TimerRepository extends JpaRepository<Timer, Long> {
    List<Timer> findByUserEmail(String email);
}
