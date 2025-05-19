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
     * Convert a {@link TimerCreateRequest} to a {@link Timer} entity
     * @param timerCreateRequest the request object containing timer creation details
     * @return a {@link Timer} entity
     */
    public Timer fromTimerCreateRequest(TimerCreateRequest timerCreateRequest) {
        return modelMapper.map(timerCreateRequest, Timer.class);

    public Timer fromTimerCreateRequest(TimerCreateRequest request) {
        return modelMapper.map(request, Timer.class);

    }

    /**
     * Convert a {@link Timer} entity to a {@link TimerDetailsResponse}
     * @param timer the {@link Timer} entity
     * @return a {@link TimerDetailsResponse}
     */
    public TimerDetailsResponse toTimerDetailsResponse(Timer timer) {
        return modelMapper.map(timer, TimerDetailsResponse.class);
    }


    /**
     * Map the fields of a {@link TimerUpdateRequest} onto a {@link Timer} entity
     * @param timerUpdateRequest the {@link TimerUpdateRequest}
     * @param timer the {@link Timer} entity
     * @return an updated {@link Timer}
     */
    public Timer updateTimerEntity(TimerUpdateRequest timerUpdateRequest, Timer timer) {
        modelMapper.map(timerUpdateRequest, timer);

    public Timer fromTimerUpdateRequest(TimerUpdateRequest request, Timer timer) {
        return modelMapper.map(request, Timer.class);
    }

    public Timer updateTimerEntity(TimerUpdateRequest request, Timer timer) {
        modelMapper.map(request, timer);

        return timer;
    }
}
