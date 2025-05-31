package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.PreferencesDto;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreference;
import org.springframework.stereotype.Component;

@Component
public class PreferencesMapper {

    public PreferencesDto toDto(TimerPreference entity) {
        PreferencesDto dto = new PreferencesDto();
        dto.setMute(entity.isMute());
        dto.setAlarmSound(entity.getAlarmSound());
        return dto;
    }

    public void updateFromDto(TimerPreference entity, PreferencesDto dto) {
        entity.setMute(dto.getMute());
        entity.setAlarmSound(dto.getAlarmSound());
    }
}