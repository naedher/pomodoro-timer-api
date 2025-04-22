package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @GetMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> getTimerById(@PathVariable Long id) {
        return ResponseEntity.ok(timerService.getTimerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> updateTimer(@PathVariable Long id, @RequestBody TimerUpdateRequest dto) {
        return ResponseEntity.ok(timerService.updateTimer(id, dto));
    }

    @PostMapping
    public ResponseEntity<TimerDetailsResponse> createTimer(@RequestBody TimerCreateRequest dto) {
        TimerDetailsResponse createdTimer = timerService.createTimer(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTimer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }
}
