package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerDetailsResponse;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimerMapper {

    private final ModelMapper modelMapper;

    public Timer fromTimerCreateRequest(TimerCreateRequest timerCreateRequest) {
        return modelMapper.map(timerCreateRequest, Timer.class);
    }

    public TimerDetailsResponse toTimerDetailsResponse(Timer timer) {
        return modelMapper.map(timer, TimerDetailsResponse.class);
    }

    public Timer fromTimerUpdateRequest(TimerUpdateRequest timerUpdateRequest, Timer timer) {
        return modelMapper.map(timerUpdateRequest, Timer.class);
    }

    public Timer updateTimerEntity(TimerUpdateRequest timerUpdateRequest, Timer timer) {
        modelMapper.map(timerUpdateRequest, timer);
        return timer;
    }
}
