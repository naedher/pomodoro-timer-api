package com.p1g14.pomodoro_timer_api.timer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    @Min(value = 5, message = "Work duration must be at least 5 minute")
    private Integer workDuration;
    @NotNull @Min(value = 1, message = "Short break duration must be at least 1 minute")
    private Integer shortBreakDuration;
    @NotNull @Min(value = 1, message = "Long break duration must be at least 1 minute")
    private Integer longBreakDuration;
    @NotNull @Min(value = 1, message = "Pomodoro count must be at least 1")
    private Integer pomodoroCount;
}
