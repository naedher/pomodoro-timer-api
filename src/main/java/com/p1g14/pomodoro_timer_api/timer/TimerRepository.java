package com.p1g14.pomodoro_timer_api.timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    List<Timer> findByUserId(Long userId);
}

