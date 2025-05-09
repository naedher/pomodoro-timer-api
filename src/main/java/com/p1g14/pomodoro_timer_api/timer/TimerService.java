package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.exception.ResourceNotFoundException;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerListResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TimerService {

    private final TimerRepository timerRepository;
    private final UserRepository userRepository;

    private final TimerMapper timerMapper;


    public List<TimerListResponse> getUserTimers() {
        User user = getCurrentUser();

        return timerRepository.findByUserEmail(user.getEmail())
                .stream().map(timerMapper::toTimerListResponse)
                .toList();
    }

    public TimerDetailsResponse getTimerById(Long id) {
        User user = getCurrentUser();
        Timer timer = getTimerValidated(id, user);
        return timerMapper.toTimerDetailsResponse(timer);
    }

    public TimerDetailsResponse updateTimer(Long id, TimerUpdateRequest timerUpdateRequest) {
        User user = getCurrentUser();
        Timer timer = getTimerValidated(id, user);

        timer = timerMapper.updateTimerEntity(timerUpdateRequest, timer);
        Timer updatedTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(updatedTimer);
    }

    public TimerDetailsResponse createTimer(TimerCreateRequest timerCreateRequest) {
        User user = getCurrentUser();

        Timer timer = timerMapper.fromTimerCreateRequest(timerCreateRequest);
        timer.setUser(user);
        timer.setCreatedAt(LocalDateTime.now());

        Timer createdTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(createdTimer);
    }

    public void deleteTimer(Long id) {
        timerRepository.deleteById(id);
    }

    private User getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    private Timer getTimerValidated(Long timerId, User user) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));

        if (!timer.isOwnedBy(user)) {
            throw new AccessDeniedException("Resource access denied");
        }
        return timer;
    }
}
