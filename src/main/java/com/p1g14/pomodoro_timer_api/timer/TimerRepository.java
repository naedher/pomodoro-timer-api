package com.p1g14.pomodoro_timer_api.timer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {
}
