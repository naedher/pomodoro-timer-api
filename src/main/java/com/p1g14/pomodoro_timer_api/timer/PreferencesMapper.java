package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreferenceDTO;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreference;
import org.springframework.stereotype.Component;

@Component
public class PreferencesMapper {

    public TimerPreferenceDTO toDto(TimerPreference entity) {
        TimerPreferenceDTO dto = new TimerPreferenceDTO();
        dto.setMute(entity.isMute());
        dto.setAlarmSound(entity.getAlarmSound());
        dto.setTheme(entity.getTheme());
        return dto;
    }

    public void updateFromDto(TimerPreference entity, TimerPreferenceDTO dto) {
        entity.setMute(dto.getMute());
        entity.setAlarmSound(dto.getAlarmSound());
        entity.setTheme(dto.getTheme());
    }
}