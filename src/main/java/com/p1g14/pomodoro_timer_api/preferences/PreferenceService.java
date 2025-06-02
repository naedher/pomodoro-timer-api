package com.p1g14.pomodoro_timer_api.preferences;

import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceResponse;
import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceUpdateRequest;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.validation.Validator;
import com.p1g14.pomodoro_timer_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final PreferencesMapper preferencesMapper;
    private final Validator validator;

    @Transactional
    public PreferenceResponse updatePreferences(PreferenceUpdateRequest request) {
        User currentUser = validator.getCurrentUser();
        
        Preference preference = preferenceRepository.findById(currentUser.getId())
                .orElse(new Preference());
        
        preference.setUser(currentUser);
        preferencesMapper.updateFromRequest(preference, request);
        
        Preference savedPreference = preferenceRepository.save(preference);
        return preferencesMapper.toResponse(savedPreference);
    }

    @Transactional(readOnly = true)
    public PreferenceResponse getPreferences() {
        User currentUser = validator.getCurrentUser();
        
        Preference preferences = preferenceRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Preferences not found for user: " + currentUser.getId()));
                
        return preferencesMapper.toResponse(preferences);
    }
} 