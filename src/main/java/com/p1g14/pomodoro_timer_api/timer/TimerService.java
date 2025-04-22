package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TimerService {

    private final TimerRepository timerRepository;

    private TimerMapper timerMapper;

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
        Timer timer = timerMapper.fromTimerCreateRequest(timerCreateRequest);

        timer.setCreatedAt(LocalDateTime.now());

        Timer createdTimer = timerRepository.save(timer);
        return timerMapper.toTimerDetailsResponse(createdTimer);
    }

    public void deleteTimer(Long id) {
        timerRepository.deleteById(id);
    }
}
