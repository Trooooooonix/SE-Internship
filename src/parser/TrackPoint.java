package parser;

import java.time.LocalDateTime;

public class TrackPoint {
    private double latitude;
    private double longitute;
    private double elevation;
    private String time;

    public TrackPoint(double lat, double lt, double ele, String time){
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

    public String getTime() {
        return time;
    }

    public String toString(){
        return "Longitude: " + this.longitute + " Lat: " + this.latitude
                + " elev: " + this.elevation +  " time: " + this.time;
    }
}
