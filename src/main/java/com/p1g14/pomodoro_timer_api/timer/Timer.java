package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a timer in the system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timers")
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private LocalDateTime createdAt;

    private Integer workDuration;

    private Integer shortBreakDuration;

    private Integer longBreakDuration;

    private Integer pomodoroCount;

    public boolean isOwnedBy(User user) {
        return this.user.getId().equals(user.getId());
    }
}
