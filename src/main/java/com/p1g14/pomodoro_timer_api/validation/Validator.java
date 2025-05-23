package com.p1g14.pomodoro_timer_api.validation;

import com.p1g14.pomodoro_timer_api.exception.ResourceNotFoundException;
import com.p1g14.pomodoro_timer_api.timer.Timer;
import com.p1g14.pomodoro_timer_api.timer.TimerRepository;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Validation class that contains utility methods used for validation of the user
 */
@RequiredArgsConstructor
@Component
public class Validator {

    private final TimerRepository timerRepository;
    private final UserRepository userRepository;

    /**
     * Get the current user email from the security context and return the corresponding user
     * @return the currently logged-in user
     * @throws UsernameNotFoundException if the user email has no corresponding user
     */
    public User getCurrentUser() {
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
    public Timer getTimerValidated(Long timerId, User user) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));

        if (!timer.isOwnedBy(user)) {
            throw new AccessDeniedException("Resource access denied");
        }
        return timer;
    }

    /**
     * Get the timer of ID and ensure that it is owned by the current user
     * @param timerId the ID of the timer
     * @return the timer of ID
     */
    public Timer getTimerValidated(Long timerId) {
        User user = getCurrentUser();
        return getTimerValidated(timerId, user);
    }
}
