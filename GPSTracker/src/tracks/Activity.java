package tracks;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class illustrates an activity.
 */
public class Activity implements Comparable<LocalDateTime> {
    /**
     * Contains the name of the activity
     */
    private String id;
    /**
     * Contains the sport type of the activity
     */
    private String sport;
    /**
     * Contains a list of laps
     */
    private List<Lap> laps;
    /**
     * Contains the date and start time of the activity
     */
    private LocalDateTime date;


    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", \nsport='" + sport + '\'' +
                ", \nlaps=" + laps +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    /**
     * This Method reads the distance from the last lap in the List of laps and returns it. This distance is the overall distance travelled.
     *
     * @return overall distance
     */
    public double getActivityDistanceMeters() {
        return Math.round(this.laps.get(this.laps.size() - 1).getDistanceMeters() * 100.00) / 100.00;
    }

    /**
     * This Method calculates the sum of seconds of the total seconds travelled in this activity and returns it.
     *
     * @return total time in seconds
     */
    public double getActivityTotalTimeSeconds() {
        double sum = 0;
        for (Lap l : laps) {
            sum += l.getTotalTimeSeconds();
        }
        return Math.round(sum * 100.0) / 100.0;
    }

    /**
     * This Method calculates the total altitude of this activity and returns it.
     *
     * @return total altitude in meters
     */
    public double getActivityTotalAltitude() {
        double sum = 0;
        double prevAltitude = 0;
        boolean firstTrackpoint = true;
        for (Lap l : laps) {
            for (Track t : l.getTracks()) {
                for (TrackPoint tp : t.getTrackPoints()) {
                    if (!firstTrackpoint && (tp.getAltitude() > prevAltitude)) {
                        sum += tp.getAltitude() - prevAltitude;
                    }
                    prevAltitude = tp.getAltitude();
                    firstTrackpoint = false;
                }
            }
        }
        return Math.round(sum * 100.0) / 100.0;

    }

    /**
     * This method calculates the maximum beats per minute (BPM) for this activity and returns it.
     *
     * @return max. BPM
     */
    public double getMaxBPM() {
        double max = 0;
        for (Lap l : laps) {
            if (max < l.getMaxBPM()) max = l.getMaxBPM();
        }
        return max;
    }

    /**
     * This method calculates the average beats per minute (BPM) for this activity and returns it.
     *
     * @return avg. BPM
     */
    public double getAvgBPM() {
        double sum = 0;
        for (Lap l : laps) {
            sum += l.getAverageBPM();
        }
        return sum / laps.size();
    }

    public void setDate() {
        this.date = laps.get(0).getStartTime();
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }

    public void addLap(Lap lap) {
        this.laps.add(lap);
    }

    @Override
    public int compareTo(LocalDateTime o) {
        if (this.getDate().isBefore(o)) return -1;
        else if (this.getDate().isAfter(o)) return 1;
        else return this.compareTo(o);
    }
}
