package markus.parserapp;

import javax.sound.midi.Track;
import java.time.LocalDateTime;

public class Trackpoint {
    private LocalDateTime timeStamp;
    private double latitide;
    private double longitude;
    private double altitude;
    private double distanceToStart;
    private double heartRateBpm;
    /*
    private String Extension;
    not used because this is an attribute for a certain kind of watch
    this extension differs from watch to watch probably
     */
    public Trackpoint(){
        super();
    }

    public Trackpoint(LocalDateTime timeStamp, double latitide, double longitude, double altitude, double distanceToStart, double heartRateBpm) {
        this.timeStamp = timeStamp;
        this.latitide = latitide;
        this.longitude = longitude;
        this.altitude = altitude;
        this.distanceToStart = distanceToStart;
        this.heartRateBpm = heartRateBpm;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getLatitide() {
        return latitide;
    }

    public void setLatitide(double latitide) {
        this.latitide = latitide;
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

    public double getDistanceToStart() {
        return distanceToStart;
    }

    public void setDistanceToStart(double distanceToStart) {
        this.distanceToStart = distanceToStart;
    }

    public double getHeartRateBpm() {
        return heartRateBpm;
    }

    public void setHeartRateBpm(double heartRateBpm) {
        this.heartRateBpm = heartRateBpm;
    }
}
