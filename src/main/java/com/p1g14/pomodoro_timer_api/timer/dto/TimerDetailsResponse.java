package com.p1g14.pomodoro_timer_api.timer.dto;

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
public class TimerDetailsResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private Integer workDuration;
    @NotNull
    private Integer breakDuration;
    @NotNull
    private Integer pomodoroCount;
}
