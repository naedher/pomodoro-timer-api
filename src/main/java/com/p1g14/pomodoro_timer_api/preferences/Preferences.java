package com.p1g14.pomodoro_timer_api.preferences;

import com.p1g14.pomodoro_timer_api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_preferences")
public class Preferences {

    @Id
    @Column(name = "user_id")
    private Long id;

    private Boolean soundEnabled;

    private String theme;

    private String notificationType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
