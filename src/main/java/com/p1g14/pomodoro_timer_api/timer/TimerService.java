package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import com.p1g14.pomodoro_timer_api.user.User;
import lombok.RequiredArgsConstructor;
import com.p1g14.pomodoro_timer_api.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing Timer-related business logic.
 */
@RequiredArgsConstructor
@Service
public class TimerService {

    private final TimerRepository timerRepository;
    private final Validator validator;

    private final TimerMapper timerMapper;


    public List<TimerDetailsResponse> getUserTimers() {
        User user = validator.getCurrentUser();

        return timerRepository.findByUserEmail(user.getEmail())
                .stream().map(timerMapper::toTimerDetailsResponse)
                .toList();
    }

    /**
     * Retrieve a timer by ID.
     * @param id the ID of the timer
     * @return the timer if found
     */
    public TimerDetailsResponse getTimerById(Long id) {
        Timer timer = validator.getTimerValidated(id);
        return timerMapper.toTimerDetailsResponse(timer);
    }

    /**
     * Update the timer of ID with the provided data.
     * @param id the ID of the timer
     * @param request the timer data to update
     * @return a DTO containing the data of the updated timer
     */
    public TimerDetailsResponse updateTimer(Long id, TimerUpdateRequest request) {
        Timer timer = validator.getTimerValidated(id);

        timer = timerMapper.updateTimerEntity(request, timer);
        Timer updatedTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(updatedTimer);
    }

    /**
     * Create a new timer and save it.
     * @param request the data of the new timer
     * @return a DTO containing the data of the created timer
     */
    public TimerDetailsResponse createTimer(TimerCreateRequest request) {
        User user = validator.getCurrentUser();

        Timer timer = timerMapper.fromTimerCreateRequest(request);
        timer.setUser(user);
        timer.setCreatedAt(LocalDateTime.now());

        Timer createdTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(createdTimer);
    }

    /**
     * Delete the timer of ID.
     * @param id the ID of the timer
     */
    public void deleteTimer(Long id) {
        // call to raise exceptions
        validator.getTimerValidated(id);

        timerRepository.deleteById(id);
    }
}
