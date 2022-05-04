package com.se.tracks;

import java.time.LocalDateTime;
import java.util.List;

public class Lap {
    private LocalDateTime startTime;
    private double totalTimeSeconds;
    private double distanceMeters;
    private double maxSpeed;
    private double calories;
    private List<Track> tracks;

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "startTime=" + startTime +
                ", \ntotalTimeSeconds=" + totalTimeSeconds +
                ", \ndistanceMeters=" + distanceMeters +
                ", \nmaxSpeed=" + maxSpeed +
                ", \ncalories=" + calories +
                ", \ntracks=" + tracks +
                "}\n";
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public double getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public void setTotalTimeSeconds(double totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }

    public double getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
