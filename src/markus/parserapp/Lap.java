package markus.parserapp;

import java.time.LocalDateTime;
import java.util.List;

public class Lap {
    private LocalDateTime lapStartTime;
    private double totalTime;
    private double distance;
    private double maxSpeed;
    private double calories;
    private double avgHeartRate_BPM;
    private double maxHeartRate_BPM;
    private String intensity;
    private List<Trackpoint> trackpoints;
    private double avgSpeed;

    public Lap(){

    }
    public Lap(LocalDateTime lapStartTime, double totalTime, double distance, double maxSpeed, double calories, double avgHeartRate_BPM, double maxHeartRate_BPM, String intensity, List<Trackpoint> trackpoints, double avgSpeed) {
        this.lapStartTime = lapStartTime;
        this.totalTime = totalTime;
        this.distance = distance;
        this.maxSpeed = maxSpeed;
        this.calories = calories;
        this.avgHeartRate_BPM = avgHeartRate_BPM;
        this.maxHeartRate_BPM = maxHeartRate_BPM;
        this.intensity = intensity;
        this.trackpoints = trackpoints;
        this.avgSpeed = avgSpeed;
    }
}
