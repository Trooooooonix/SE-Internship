package parser;

import java.time.LocalDateTime;
import java.util.Date;

public class TrackPoint {
    private double latitude;
    private double longitute;
    private double elevation;
    private Date time;

    public TrackPoint(double lat, double lt, double ele, Date time){
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

    public Date getTime() {
        return time;
    }

    public String toString(){
        return "Longitude: " + this.longitute + " Lat: " + this.latitude
                + " elev: " + this.elevation +  " time: " + this.time;
    }
}
