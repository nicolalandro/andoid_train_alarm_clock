package com.example.mint.alarmclock.SharedPreferences;

public class AlarmState {
    private int hour;
    private int minutes;
    private boolean active;
    private String intentUri;

    public AlarmState(int hour, int minutes, boolean active) {
        this.hour = hour;
        this.minutes = minutes;
        this.active = active;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIntentUri() {
        return intentUri;
    }

    public void setIntentUri(String intentUri) {
        this.intentUri = intentUri;
    }
}

