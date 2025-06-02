package com.p1g14.pomodoro_timer_api.preferences;

import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceResponse;
import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<PreferenceResponse> getPreferences() {
        return ResponseEntity.ok(preferenceService.getPreferences());
    }

    @PutMapping
    public ResponseEntity<PreferenceResponse> updatePreferences(
            @Valid @RequestBody PreferenceUpdateRequest request) {
        return ResponseEntity.ok(preferenceService.updatePreferences(request));
    }
} 