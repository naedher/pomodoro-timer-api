package com.p1g14.pomodoro_timer_api.timer_order;

import com.p1g14.pomodoro_timer_api.timer.Timer;
import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timers_order")
public class TimerOrder {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "timer_id")
    private Timer timer;

    private Integer position;
}
