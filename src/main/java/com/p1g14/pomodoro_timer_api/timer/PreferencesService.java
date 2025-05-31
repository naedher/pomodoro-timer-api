package com.p1g14.pomodoro_timer_api.timer;

package com.example.pomodoro.controller;

import com.p1g14.pomodoro_timer_api.timer.dto.PreferencesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@Validated
public class PreferencesController {

    private final PreferencesService service;

    public PreferencesController(PreferencesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PreferencesDto> load(@RequestParam Long userId) {
        PreferencesDto prefs = service.load(userId);
        return ResponseEntity.ok(prefs);
    }

    @PutMapping
    public ResponseEntity<PreferencesDto> save(
            @RequestParam Long userId,
            @Valid @RequestBody PreferencesDto dto) {
        PreferencesDto updated = service.save(userId, dto);
        return ResponseEntity.ok(updated);
    }
}
