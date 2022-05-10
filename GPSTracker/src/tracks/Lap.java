package tracks;

import java.time.LocalDateTime;
import java.util.List;

public class Lap {
    private LocalDateTime startTime;
    private double totalTimeSeconds;
    private double distanceMeters;
    private double maxSpeed;
    private double calories;
    private double averageBPM;
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

    public String getTotalTimeHHmmSS(double seconds){
        return String.format("%02d:%02d:%02d", (int)seconds / 3600, ((int)seconds % 3600) / 60, ((int)seconds % 60));
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

    public double getAverageBPM() {
        return averageBPM;
    }

    public void setAverageBPM(double averageBPM) {
        this.averageBPM = averageBPM;
    }

    public double getLapTotalAltitude() {
        double sum = 0;
        double prevAltitude = 0;
        boolean firstTrackpoint = true;
        for (Track t : getTracks()) {
            for (TrackPoint tp : t.getTrackPoints()) {
                if (!firstTrackpoint && (tp.getAltitude() > prevAltitude)) {
                    sum += tp.getAltitude() - prevAltitude;
                }
                prevAltitude = tp.getAltitude();
                firstTrackpoint = false;
            }
        }

        return Math.round(sum * 100.0) / 100.0;
    }
}
