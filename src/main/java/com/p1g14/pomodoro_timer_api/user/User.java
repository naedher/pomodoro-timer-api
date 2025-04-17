package com.p1g14.pomodoro_timer_api.user;

import com.p1g14.pomodoro_timer_api.preferences.Preferences;
import com.p1g14.pomodoro_timer_api.session.Session;
import com.p1g14.pomodoro_timer_api.timer_order.TimerOrder;
import com.p1g14.pomodoro_timer_api.timer.Timer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Preferences preferences;

    @OneToMany(mappedBy = "user")
    private Set<Session> sessions;

    @OneToMany(mappedBy = "user")
    private Set<Timer> timers;

    @OneToMany(mappedBy = "user")
    private Set<TimerOrder> timerOrders;
}
