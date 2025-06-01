package com.p1g14.pomodoro_timer_api.timer.repository;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerPreferenceRepository extends JpaRepository<TimerPreference, Long> {
}