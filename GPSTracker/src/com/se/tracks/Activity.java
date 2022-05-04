package com.se.tracks;

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

    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }

    public void addLap(Lap lap) {
        this.laps.add(lap);
    }
}
