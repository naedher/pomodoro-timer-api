package com.p1g14.pomodoro_timer_api.preferences;

import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceResponse;
import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreferencesMapper {
    private final ModelMapper modelMapper;

    public PreferenceResponse toResponse(Preference entity) {
        return modelMapper.map(entity, PreferenceResponse.class);
    }

    public void updateFromRequest(Preference entity, PreferenceUpdateRequest request) {
        modelMapper.map(request, entity);
    }
}