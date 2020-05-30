package com.example.pomodoit;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.Instant;

public class TimerModel {
    // Properties
    private Duration timerDuration;
    private boolean isTimerStarted;
    private boolean isTimerPaused;
    private Duration timerElapsed;
    private Instant timerStartTime;

    public TimerModel(Duration timerDuration) {
        this.timerDuration = timerDuration;
        this.timerElapsed = Duration.ofSeconds(0);
        this.timerStartTime = null;
        this.isTimerStarted = false;
        this.isTimerPaused = false;
    }


    // Data Access
    public String getTimerString() {
        if (isTimerStarted) {
            Duration timeRemaining;
            if (isTimerPaused) {
                timeRemaining = timerDuration.minus(timerElapsed);
            } else {
                Duration timeSoFar = timerElapsed.plus(
                        Duration.between(timerStartTime, Instant.now()));
                timeRemaining = timerDuration.minus(timeSoFar);
            }
            return formatTimerString(timeRemaining);
        } else {
            return formatTimerString(timerDuration);
        }
    }


    // Timer Controls
    void start() {
        isTimerStarted = true;
        timerStartTime = Instant.now();
    }

    void pause() {
        isTimerPaused = true;
        timerElapsed.plus(Duration.between(timerStartTime, Instant.now()));
    }

    void resume() {
        isTimerPaused = false;
        timerStartTime = Instant.now();
    }

    void reset(Duration duration) {
        timerDuration = duration;
        this.timerElapsed = Duration.ofSeconds(0);
        timerStartTime = null;
        this.isTimerStarted = false;
        this.isTimerPaused = false;
    }


    // Private Methods
    private String formatTimerString(Duration remaining) {
        long totalSeconds = remaining.getSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%d", minutes, seconds);
    }
}
