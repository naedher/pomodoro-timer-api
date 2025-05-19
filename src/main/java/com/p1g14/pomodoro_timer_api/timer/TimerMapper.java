package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
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
     * @param request the request object containing timer creation details
     * @return a Timer entity
     */
    public Timer fromTimerCreateRequest(TimerCreateRequest request) {
        return modelMapper.map(request, Timer.class);
    }

    /**
     * Convert a Timer entity to a TimerDetailsResponse
     * @param timer the Timer entity
     * @return
     */
    public TimerDetailsResponse toTimerDetailsResponse(Timer timer) {
        return modelMapper.map(timer, TimerDetailsResponse.class);
    }

    public Timer fromTimerUpdateRequest(TimerUpdateRequest request, Timer timer) {
        return modelMapper.map(request, Timer.class);
    }

    public Timer updateTimerEntity(TimerUpdateRequest request, Timer timer) {
        modelMapper.map(request, timer);
        return timer;
    }
}
