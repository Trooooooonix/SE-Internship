package tracks;

import java.util.List;

public class Activity {
    private String id;
    private String sport;
    private List<Lap> laps;


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

    public double getActivityDistanceMeters(){
        double sum= 0;
        for(Lap l : laps){
            sum += l.getDistanceMeters();
            //System.out.println("average BPM: " + l.getAverageBPM());      // TEST
        }
        return Math.round(sum*100.0)/100.0;
    }

    public double getActivityTotalTimeSeconds(){
        double sum= 0;
        for(Lap l : laps){
            sum += l.getTotalTimeSeconds();
        }
        return Math.round(sum*100.0)/100.0;
    }
    public String getTotalTimeHHmmSS(double seconds){
        return String.format("%02d:%02d:%02d", (int)seconds / 3600, ((int)seconds % 3600) / 60, ((int)seconds % 60));
    }



    public double getActivityTotalAltitude(){
        double sum= 0;
        double prevAltitude = 0;
        boolean firstTrackpoint = true;
        for(Lap l : laps){
            for(Track t : l.getTracks()){
                for(TrackPoint tp : t.getTrackPoints()){
                    if (!firstTrackpoint && (tp.getAltitude() > prevAltitude)) {
                        sum += tp.getAltitude() - prevAltitude;
                    }
                    prevAltitude = tp.getAltitude();
                    firstTrackpoint = false;
                }
            }
        }
        return Math.round(sum*100.0)/100.0;

    }

    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }

    public void addLap(Lap lap) {
        this.laps.add(lap);
    }
}
