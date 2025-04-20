package com.p1g14.pomodoro_timer_api.timer;

import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private Integer breakDuration;

    private Integer pomodoroCount;

    /**
     * Sets the user and maintains both sides of the relationship.
     */
    public void setUser(User user) {

        if (this.user != null) {
            this.user.getTimers().remove(this);
        }
        this.user = user;
        if (user != null) {
            user.getTimers().add(this);
        }
    }
}
