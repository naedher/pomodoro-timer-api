package com.p1g14.pomodoro_timer_api.timer.service;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreferenceDTO;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreference;
import com.p1g14.pomodoro_timer_api.timer.repository.TimerPreferenceRepository;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimerPreferenceService {

    private final TimerPreferenceRepository timerPreferenceRepository;
    private final UserRepository userRepository;

    @Transactional
    public TimerPreferenceDTO savePreferences(Long userId, TimerPreferenceDTO preferences) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TimerPreference timerPreference = timerPreferenceRepository.findById(userId)
                .orElse(new TimerPreference());

        timerPreference.setUser(user);
        timerPreference.setMute(preferences.getMute());
        timerPreference.setAlarmSound(preferences.getAlarmSound());
        timerPreference.setTheme(preferences.getTheme());

        TimerPreference savedPreference = timerPreferenceRepository.save(timerPreference);
        return convertToDTO(savedPreference);
    }

    @Transactional(readOnly = true)
    public TimerPreferenceDTO getPreferences(Long userId) {
        TimerPreference preferences = timerPreferenceRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Preferences not found"));
        return convertToDTO(preferences);
    }

    private TimerPreferenceDTO convertToDTO(TimerPreference preferences) {
        return TimerPreferenceDTO.builder()
                .mute(preferences.isMute())
                .alarmSound(preferences.getAlarmSound())
                .theme(preferences.getTheme())
                .build();
    }
}