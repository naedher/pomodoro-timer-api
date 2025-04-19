package com.p1g14.pomodoro_timer_api.user;

import com.p1g14.pomodoro_timer_api.timer.Timer;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "user")
    private Set<Timer> timers;
}
