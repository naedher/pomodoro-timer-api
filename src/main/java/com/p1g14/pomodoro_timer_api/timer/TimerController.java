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

/**
 * REST controller for managing timers.
 */
@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    /**
     * Retrieve a list of all timers owned by the current user
     * @return HTTP 200 OK response containing a list of timer details
     */
    @GetMapping
    public ResponseEntity<List<TimerDetailsResponse>> getUserTimers() {
        return ResponseEntity.ok(timerService.getUserTimers());
    }

    /**
     * Retrieve a timer by ID.
     * @param id the ID of the timer
     * @return HTTP 200 OK response containing the timer details
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> getTimerById(@PathVariable Long id) {
        return ResponseEntity.ok(timerService.getTimerById(id));
    }

    /**
     * Update the timer of ID with the provided data.
     * @param id the ID of the timer
     * @param dto the timer data to update
     * @return HTTP 200 OK response containing the timer details
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimerDetailsResponse> updateTimer(@PathVariable Long id, @RequestBody @Valid TimerUpdateRequest request) {
        return ResponseEntity.ok(timerService.updateTimer(id, request));
    }

    //Spring will automatically trigger bean‐validation when we annotate the @RequestBody parameter with @Valid

    /**
     * Create a new timer and save it.
     * @param dto the data of the new timer
     * @return HTTP 201 CREATED response containing a link to the created timer
     */
    @PostMapping
    public ResponseEntity<TimerDetailsResponse> createTimer(@RequestBody @Valid TimerCreateRequest request) {
        TimerDetailsResponse createdTimer = timerService.createTimer(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTimer.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    /**
     * Delete the timer of ID.
     * @param id the ID of the timer
     * @return HTTP 204 NO CONTENT response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }
}
