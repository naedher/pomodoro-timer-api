package com.p1g14.pomodoro_timer_api.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerUpdateRequest {
    private Long id;
    private String name;
    private Integer workDuration;
    private Integer breakDuration;
    private Integer pomodoroCount;
}
