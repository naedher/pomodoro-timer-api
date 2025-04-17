package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.session.Session;
import com.p1g14.pomodoro_timer_api.timer_order.TimerOrder;
import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timers")
public class Timer {

    @Id
    @GeneratedValue
    @Column(name = "timer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private Integer workDuration;

    private Integer breakDuration;

    private Integer pomodoroCount;

    @OneToMany(mappedBy = "timer")
    private Set<TimerOrder> timerOrderSet;

    @OneToMany(mappedBy = "timer")
    private Set<Session> sessions;
}
