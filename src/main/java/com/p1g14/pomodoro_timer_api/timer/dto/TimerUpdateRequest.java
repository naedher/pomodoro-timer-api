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
public class TimerUpdateRequest {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private Integer workDuration;
    private Integer breakDuration;
    private Integer pomodoroCount;
}
