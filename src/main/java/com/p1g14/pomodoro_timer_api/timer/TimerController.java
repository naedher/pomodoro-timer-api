package com.p1g14.pomodoro_timer_api.timer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @GetMapping
    public ResponseEntity<List<Timer>> getTimers() {
        return ResponseEntity.ok(timerService.getTimers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timer> getTimerById(Long id) {
        return ResponseEntity.ok(timerService.getTimerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timer> updateTimer(@PathVariable Long id, @RequestBody Timer newTimer) {
        return ResponseEntity.ok(timerService.updateTimer(newTimer));
    }

    @PostMapping
    public ResponseEntity<Timer> createTimer(@RequestBody Timer timer) {
        Timer createdTimer = timerService.createTimer(timer);

        URI uri = null;
        try {
            uri = new URI(String.format("/%d", createdTimer.getId()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e); // handle this gracefully
        }
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }




}
