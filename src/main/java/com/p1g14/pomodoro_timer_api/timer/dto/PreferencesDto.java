package com.p1g14.pomodoro_timer_api.timer.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PreferencesDto {

    @NotNull
    private Boolean mute;

    @NotBlank
    private String alarmSound;

    // e.g. “alarm1.mp3”, “alarm2.mp3”, …

    // getters & setters
    public Boolean getMute() { return mute; }
    public void setMute(Boolean mute) { this.mute = mute; }

    public String getAlarmSound() { return alarmSound; }
    public void setAlarmSound(String alarmSound) { this.alarmSound = alarmSound; }
}
