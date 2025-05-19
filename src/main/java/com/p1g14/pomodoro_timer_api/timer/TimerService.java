package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.exception.ResourceNotFoundException;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import com.p1g14.pomodoro_timer_api.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


    /**
     * Retrieve a list of timers owned by the current user
     * @return a list of timers
     */
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
     * @param timerUpdateRequest the timer data to update
     * @return a DTO containing the data of the updated timer
     */
    public TimerDetailsResponse updateTimer(Long id, TimerUpdateRequest timerUpdateRequest) {
        User user = getCurrentUser();
        Timer timer = getTimerValidated(id, user);

   User user = getCurrentUser();
   Timer timer = validator.getTimerValidated(id, user);


        timer = timerMapper.updateTimerEntity(request, timer);
        Timer updatedTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(updatedTimer);
    }

    /**
     * Create a new timer and save it.
     * @param timerCreateRequest the data of the new timer
     * @return a DTO containing the data of the created timer
     */
    public TimerDetailsResponse createTimer(TimerCreateRequest timerCreateRequest) {
        User user = getCurrentUser();

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


    /**
     * Get the current user email from the security context and return the corresponding user
     * @return the currently logged-in user
     * @throws UsernameNotFoundException if the user email has no corresponding user
     */
    private User getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    /**
     * Get the timer of ID and ensure that it is owned by the user
     * @param timerId the ID of the timer
     * @param user the user that owns the timer
     * @return the timer of ID
     * @throws ResourceNotFoundException if timer of ID does not exist
     * @throws AccessDeniedException if the timer is not owned by user
     */
    private Timer getTimerValidated(Long timerId, User user) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));

        if (!timer.isOwnedBy(user)) {
            throw new AccessDeniedException("Resource access denied");
        }
        return timer;
    }
}
