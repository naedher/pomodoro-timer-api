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

@RequiredArgsConstructor
@Component
public class Validator {

    private final TimerRepository timerRepository;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    public Timer getTimerValidated(Long timerId, User user) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));

        if (!timer.isOwnedBy(user)) {
            throw new AccessDeniedException("Resource access denied");
        }
        return timer;
    }

    public Timer getTimerValidated(Long timerId) {
        User user = getCurrentUser();
        return getTimerValidated(timerId, user);
    }
}
