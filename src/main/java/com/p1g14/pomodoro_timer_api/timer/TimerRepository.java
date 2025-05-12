package com.p1g14.pomodoro_timer_api.timer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Timer entities.
 * Provides CRUD operations and query method support.
 */
public interface TimerRepository extends JpaRepository<Timer, Long> {
}
