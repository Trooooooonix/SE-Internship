package tracks;

import java.time.LocalDateTime;

/**
 * This class illustrates a TrackPoint. TrackPoints are part of a track.
 */
public class TrackPoint {
    /**
     * Contains the exact date and time of this TrackPoint
     */
    private LocalDateTime time;
    /**
     * Contains the latitude where this TrackPoint was recorded
     */
    private double latitude;
    /**
     * Contains the longitude where this TrackPoint was recorded
     */
    private double longitude;
    /**
     * Contains the altitude where this TrackPoint was recorded
     */
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
