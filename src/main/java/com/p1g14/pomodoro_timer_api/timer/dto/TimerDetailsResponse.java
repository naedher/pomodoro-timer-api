package com.p1g14.pomodoro_timer_api.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerDetailsResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private Integer workDuration;
    private Integer shortBreakDuration;
    private Integer longBreakDuration;
    private Integer pomodoroCount;
}
