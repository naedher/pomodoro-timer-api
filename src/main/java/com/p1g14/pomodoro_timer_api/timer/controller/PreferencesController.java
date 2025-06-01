package com.p1g14.pomodoro_timer_api.timer.controller;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerPreferenceDTO;
import com.p1g14.pomodoro_timer_api.timer.service.TimerPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/preferences")
@Validated
@RequiredArgsConstructor
public class PreferencesController {

    private final TimerPreferenceService timerPreferenceService;

    @GetMapping("/{userId}")
    public ResponseEntity<TimerPreferenceDTO> getPreferences(@PathVariable Long userId) {
        try {
            TimerPreferenceDTO preferences = timerPreferenceService.getPreferences(userId);
            return ResponseEntity.ok(preferences);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<TimerPreferenceDTO> savePreferences(
            @PathVariable Long userId,
            @Valid @RequestBody TimerPreferenceDTO preferences) {
        try {
            TimerPreferenceDTO savedPreferences = timerPreferenceService.savePreferences(userId, preferences);
            return ResponseEntity.ok(savedPreferences);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}