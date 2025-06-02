package com.p1g14.pomodoro_timer_api.preferences.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceResponse {
    private Boolean mute;
    private String alarmSound;
    private String theme;
} 