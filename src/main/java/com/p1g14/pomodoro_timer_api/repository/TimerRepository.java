package com.p1g14.pomodoro_timer_api.repository;

// src/main/java/com.p1g14/pomodoro_timer_api/timer/TimerRepository.java


import com.p1g14.pomodoro_timer_api.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    List<Timer> findByUserId(Long userId);
}

