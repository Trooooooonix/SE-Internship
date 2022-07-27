package tracks;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class illustrates a lap. Laps are part of an activity.
 */
public class Lap {
    /**
     * Contains the time and date of this lap
     */
    private LocalDateTime startTime;
    /**
     * Contains the seconds needed for this lap
     */
    private double totalTimeSeconds;
    /**
     * Contains the meters travelled in this lap
     */
    private double distanceMeters;
    /**
     * Contains the maximum speed reached in this lap
     */
    private double maxSpeed;
    /**
     * Contains the calories burned in this lap
     */
    private double calories;
    /**
     * Contains the average beats per minute (BPM) in this lap
     */
    private double averageBPM;
    /**
     * Contains the maximum beats per minute (BPM) in this lap
     */
    private double maxBPM;
    /**
     * Contains a list of tracks
     */
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
                ", totalTimeSeconds=" + totalTimeSeconds +
                ", distanceMeters=" + distanceMeters +
                ", maxSpeed=" + maxSpeed +
                ", calories=" + calories +
                ", averageBPM=" + averageBPM +
                ", maxBPM=" + maxBPM +
                ", tracks=" + tracks +
                '}';
    }

    public double getMaxBPM() {
        return maxBPM;
    }

    public void setMaxBPM(double maxBPM) {
        this.maxBPM = maxBPM;
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

    public double getAverageBPM() {
        return averageBPM;
    }

    public void setAverageBPM(double averageBPM) {
        this.averageBPM = averageBPM;
    }

    /**
     * This method calculates the total altitude of this lap and returns it.
     *
     * @return altitude of this lap
     */
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
