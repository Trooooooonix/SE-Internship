package tracks;

import java.util.List;

/**
 * This class illustrates a track. Tracks are part of a lap.
 */
public class Track {
    /**
     * Tracks contain a list of trackpoints.
     */
    List<TrackPoint> trackPoints;

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(List<TrackPoint> trackPoints) {
        this.trackPoints = trackPoints;
    }

    @Override
    public String toString() {
        return "Track{" +
                "\ntrackPoints=" + trackPoints +
                '}';
    }
}
