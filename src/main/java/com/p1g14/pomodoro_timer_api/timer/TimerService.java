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

    public TimerDetailsResponse getTimerById(Long id) {
        Timer timer = validator.getTimerValidated(id);
        return timerMapper.toTimerDetailsResponse(timer);
    }

    public TimerDetailsResponse updateTimer(Long id, TimerUpdateRequest request) {
        Timer timer = validator.getTimerValidated(id);

        timer = timerMapper.updateTimerEntity(request, timer);
        Timer updatedTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(updatedTimer);
    }

    public TimerDetailsResponse createTimer(TimerCreateRequest request) {
        User user = validator.getCurrentUser();

        Timer timer = timerMapper.fromTimerCreateRequest(request);
        timer.setUser(user);
        timer.setCreatedAt(LocalDateTime.now());

        Timer createdTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(createdTimer);
    }

    public void deleteTimer(Long id) {
        // call to raise exceptions
        validator.getTimerValidated(id);

        timerRepository.deleteById(id);
    }
}
