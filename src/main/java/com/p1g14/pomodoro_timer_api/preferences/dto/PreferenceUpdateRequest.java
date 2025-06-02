package com.p1g14.pomodoro_timer_api.preferences.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceUpdateRequest {
    @NotNull
    private boolean mute;

    @NotBlank
    private String alarmSound;

    @NotBlank
    private String theme;
} 