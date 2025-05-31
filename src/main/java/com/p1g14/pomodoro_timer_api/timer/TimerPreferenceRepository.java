package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimerPreferenceRepository extends JpaRepository<TimerPreference, Long> {
    Optional<TimerPreference> findByUserId(Long userId);
}

