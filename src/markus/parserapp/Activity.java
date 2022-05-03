package markus.parserapp;

import java.time.LocalDateTime;
import java.util.List;

public class Activity {
    private String sport;
    private String id;
    private List<Lap> result;

    public Activity(String sport, String id, List<Lap> result) {
        this.sport = sport;
        this.id = id;
        this.result = result;
    }

    public Activity() {
        super();
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Lap> getResult() {
        return result;
    }

    public void setResult(List<Lap> result) {
        this.result = result;
    }

    /*private LocalDateTime lapStartTime;
    private double totalLapTime;
    private double totalLapDistance;
    private double maximumlapSpeed;



    private double maxLapSpeed;
    private double lapcalories;
    private double lapAvgHeartBPM;
    private double lapMaxHeartBPM;

    /*private String id;

    public Activity(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/
}
