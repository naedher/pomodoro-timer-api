package com.p1g14.pomodoro_timer_api.preferences;

import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timer_preferences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Preference {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean mute;

    @Column(nullable = false)
    private String alarmSound;

    @Column(nullable = false)
    private String theme;
}

