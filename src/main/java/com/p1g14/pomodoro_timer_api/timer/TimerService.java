package com.p1g14.pomodoro_timer_api.timer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TimerService {

    private final TimerRepository timerRepository;

    public List<Timer> getTimers() {
        return timerRepository.findAll();
    }

    public Timer getTimerById(Long id) {
        return timerRepository.getReferenceById(id);
    }

    public Timer updateTimer(Timer newTimer) {
        newTimer.setUpdatedAt(LocalDateTime.now());
        return timerRepository.save(newTimer);
    }

    public Timer createTimer(Timer timer) {
        return timerRepository.save(timer);
    }

    public void deleteTimer(Long id) {
        timerRepository.deleteById(id);
    }
}
