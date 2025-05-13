package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<TimerDetailsResponse>> getUserTimers() {
        return ResponseEntity.ok(timerService.getUserTimers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> getTimerById(@PathVariable Long id) {
        return ResponseEntity.ok(timerService.getTimerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> updateTimer(@PathVariable Long id, @RequestBody @Valid TimerUpdateRequest dto) {
        return ResponseEntity.ok(timerService.updateTimer(id, dto));
    }
    //Spring will automatically trigger bean‐validation when we annotate the @RequestBody parameter with @Valid
    @PostMapping
    public ResponseEntity<TimerDetailsResponse> createTimer(@RequestBody @Valid TimerCreateRequest dto) {
        TimerDetailsResponse createdTimer = timerService.createTimer(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTimer.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }
}
