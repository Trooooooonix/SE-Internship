package tracks;

import java.util.List;

public class Track {
    List<TrackPoint> trackPoints;

    public void addTrackPoint(TrackPoint trackPoint){
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
