package com.se.oldParser;

import java.time.LocalDateTime;

public class GpxTrackPoint {
    private double latitude;
    private double longitute;
    private double elevation;
    private LocalDateTime time;

    public GpxTrackPoint(double lat, double lt, double ele, LocalDateTime time){
        this.latitude = lat;
        this.longitute = lt;
        this.elevation = ele;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitute() {
        return longitute;
    }

    public double getElevation() {
        return elevation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String toString(){
        return "Longitude: " + this.longitute + " Lat: " + this.latitude
                + " elev: " + this.elevation +  " time: " + this.time;
    }
}
