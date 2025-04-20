package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerListResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public List<TimerListResponse> getAllTimers() {

        List<Timer> timers = timerRepository.findAll();

        return timers.stream()
                .map(timerMapper::toTimerListResponse)
                .toList();
    }

    public TimerDetailsResponse getTimerById(Long id) {
        Timer timer = timerRepository.getReferenceById(id);
        return timerMapper.toTimerDetailsResponse(timer);
    }

    public TimerDetailsResponse updateTimer(Long id, TimerUpdateRequest timerUpdateRequest) {
        Timer timer = timerRepository.getReferenceById(id);
        timer = timerMapper.updateTimerEntity(timerUpdateRequest, timer);
        Timer updatedTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(updatedTimer);
    }

    public TimerDetailsResponse createTimer(TimerCreateRequest timerCreateRequest) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        Timer timer = timerMapper.fromTimerCreateRequest(timerCreateRequest);
        timer.setUser(user);
        timer.setCreatedAt(LocalDateTime.now());

        Timer createdTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(createdTimer);
    }

    public void deleteTimer(Long id) {
        timerRepository.deleteById(id);
    }
}
