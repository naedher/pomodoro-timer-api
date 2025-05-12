package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerListResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Explicit mapper class for mapping between Timer entities and DTOs.
 */
@Component
@RequiredArgsConstructor
public class TimerMapper {

    private final ModelMapper modelMapper;

    /**
     * Convert a TimerCreateRequest to a Timer entity
     * @param timerCreateRequest the request object containing timer creation details
     * @return a Timer entity
     */
    public Timer fromTimerCreateRequest(TimerCreateRequest timerCreateRequest) {
        return modelMapper.map(timerCreateRequest, Timer.class);
    }

    /**
     * Convert a Timer entity to a TimerDetailsResponse
     * @param timer the Timer entity
     * @return 
     */
    public TimerDetailsResponse toTimerDetailsResponse(Timer timer) {
        return modelMapper.map(timer, TimerDetailsResponse.class);
    }

    public TimerListResponse toTimerListResponse(Timer timer) {
        return modelMapper.map(timer, TimerListResponse.class);
    }

    public Timer fromTimerUpdateRequest(TimerUpdateRequest timerUpdateRequest, Timer timer) {
        return modelMapper.map(timerUpdateRequest, Timer.class);
    }

    public Timer updateTimerEntity(TimerUpdateRequest timerUpdateRequest, Timer timer) {
        modelMapper.map(timerUpdateRequest, timer);
        return timer;
    }
}
