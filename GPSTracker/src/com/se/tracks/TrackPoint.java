package com.se.tracks;

import java.time.LocalDateTime;

public class TrackPoint {
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double altitude;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "TrackPoint{" +
                "time=" + time +
                ", \nlatitude=" + latitude +
                ", \nlongitude=" + longitude +
                ", \naltitude=" + altitude +
                '}';
    }
}
