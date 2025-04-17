package com.p1g14.pomodoro_timer_api.timer;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TimerService {

    private final TimerRepository timerRepository;

    private final ModelMapper modelMapper;

    public List<Timer> getTimers() {
        return timerRepository.findAll();
    }

    public TimerDto updateTimer(TimerDto newTimerDto) {
        Timer newTimer = DtoToEntity(newTimerDto);
        Timer oldTimer = timerRepository.getReferenceById(newTimerDto.getId());

        newTimer.setCreatedAt(oldTimer.getCreatedAt());
        newTimer.setUpdatedAt(LocalDateTime.now());

        Timer updatedTimer = timerRepository.save(newTimer);
        return EntityToDto(updatedTimer);
    }

    private Timer DtoToEntity(TimerDto timerDto) {
        return modelMapper.map(timerDto, Timer.class);
    }


    private TimerDto EntityToDto(Timer timer) {
        return modelMapper.map(timer, TimerDto.class);
    }

}
