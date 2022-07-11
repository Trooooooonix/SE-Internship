package handlers;

import tracks.Activity;
import tracks.Lap;
import tracks.Track;
import tracks.TrackPoint;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityHandler extends DefaultHandler {
    private static final String ACTIVITY = "Activity";
    private static final String ID = "Id";
    private static final String LAP = "Lap";
    private static final String TOTALTIMESECONDS = "TotalTimeSeconds";
    private static final String DISTANCEMETERS = "DistanceMeters";
    private static final String MAXIMUMSPEED = "MaximumSpeed";
    private static final String CALORIES = "Calories";
    private static final String TRACK = "Track";
    private static final String TRACKPOINT = "Trackpoint";
    private static final String TIME = "Time";
    private static final String LATITUDE = "LatitudeDegrees";
    private static final String LONGITUDE = "LongitudeDegrees";
    private static final String ALTITUDE = "AltitudeMeters";
    private static final String MAXIMUMBPM = "MaximumHeartRateBpm";
    private static final String AVERAGEBPM = "AverageHeartRateBpm";
    private static final String VALUE = "Value";

    private Activity a;
    private Lap currLap;
    private Track currTrack;
    private TrackPoint currTrackPoint;
    private StringBuilder elementValue;
    private boolean maxBPM_bool = false;
    private boolean avgBPM_bool = false;

    @Override
    public void startDocument(){
        a = new Activity();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        switch (qName) {
            case ACTIVITY -> {
                a.setSport(attributes.getValue(0));
                a.setLaps(new ArrayList<>());
            }
            case LAP -> {
                currLap = new Lap();
                currLap.setTracks(new ArrayList<>());
                currLap.setStartTime(LocalDateTime.parse(attributes.getValue(0).substring(0, attributes.getValue(0).length() - 1)));
            }
            case TRACK -> {
                currTrack = new Track();
                currTrack.setTrackPoints(new ArrayList<>());
            }
            case TRACKPOINT -> currTrackPoint = new TrackPoint();
            case MAXIMUMBPM -> maxBPM_bool = true;
            case AVERAGEBPM -> avgBPM_bool = true;
            case ID, TOTALTIMESECONDS, DISTANCEMETERS, MAXIMUMSPEED, CALORIES, TIME, LATITUDE, LONGITUDE, ALTITUDE, VALUE -> elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        switch (qName) {
            case ID:
                a.setId(elementValue.toString());
                break;
            case LAP:
                a.addLap(currLap);
                break;
            case TOTALTIMESECONDS:
                currLap.setTotalTimeSeconds(Double.parseDouble(elementValue.toString()));
                break;
            case DISTANCEMETERS:
                // look at lesbos for || argument
                if (currLap.getTracks().size() == 0 || !currLap.getTracks().contains(currTrack)) {
                    currLap.setDistanceMeters(Double.parseDouble(elementValue.toString()));
                }
                break;
            case MAXIMUMSPEED:
                currLap.setMaxSpeed(Double.parseDouble(elementValue.toString()));
                break;
            case CALORIES:
                currLap.setCalories(Double.parseDouble(elementValue.toString()));
                break;
            case AVERAGEBPM:
                avgBPM_bool = false;
                break;
            case MAXIMUMBPM:
                maxBPM_bool = false;
                break;
            case VALUE:
                if (maxBPM_bool) currLap.setMaxBPM(Double.parseDouble(elementValue.toString()));
                if (avgBPM_bool) currLap.setAverageBPM(Double.parseDouble(elementValue.toString()));
                break;
            case TRACK:
                currLap.addTrack(currTrack);
                break;
            case TRACKPOINT:
                currTrack.addTrackPoint(currTrackPoint);
                break;
            case TIME:
                String eV = elementValue.toString();
                try {
                    currTrackPoint.setTime(LocalDateTime.parse(eV.substring(0, eV.length() - 1)));
                } catch (Exception ignored) {
                }
                break;
            case LATITUDE:
                currTrackPoint.setLatitude(Double.parseDouble(elementValue.toString()));
                break;
            case LONGITUDE:
                currTrackPoint.setLongitude(Double.parseDouble(elementValue.toString()));
                break;
            case ALTITUDE:
                currTrackPoint.setAltitude(Double.parseDouble(elementValue.toString()));
                break;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (elementValue == null) elementValue = new StringBuilder();
        else elementValue.append(ch, start, length);
    }

    public Activity getActivity() {
        if (a == null) {
            Logging.print("File not added");
            return null;
        } else {
            Logging.print("File added");
            Logging.print(a.toString());
            return a;
        }
    }
}
