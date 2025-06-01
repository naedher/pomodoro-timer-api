package com.p1g14.pomodoro_timer_api.timer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerPreferenceDTO {
    @NotNull
    private Boolean mute;

    @NotBlank
    private String alarmSound;

    @NotBlank
    private String theme;
} 