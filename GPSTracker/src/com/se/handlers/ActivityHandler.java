package com.se.handlers;

import com.se.tracks.Activity;
import com.se.tracks.Lap;
import com.se.tracks.Track;
import com.se.tracks.TrackPoint;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private Activity a;
    private Lap currLap;
    private Track currTrack;
    private TrackPoint currTrackPoint;
    private StringBuilder elementValue;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void startDocument() throws SAXException {
        a = new Activity();
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // here starts the saving of the imported file
        switch (qName) {
            case ACTIVITY:
                a.setSport(attributes.getValue(0));
                a.setLaps(new ArrayList<>());
                break;
            case ID:
                elementValue = new StringBuilder();
                break;
            case LAP:
                currLap = new Lap();
                currLap.setTracks(new ArrayList<>());
                currLap.setStartTime(LocalDateTime.parse(attributes.getValue(0).substring(0, attributes.getValue(0).length() - 1)));
                break;
            case TOTALTIMESECONDS:
                elementValue = new StringBuilder();
                break;
            case DISTANCEMETERS:
                elementValue = new StringBuilder();
                break;
            case MAXIMUMSPEED:
                elementValue = new StringBuilder();
                break;
            case CALORIES:
                elementValue = new StringBuilder();
                break;
            case TRACK:
                currTrack = new Track();
                currTrack.setTrackPoints(new ArrayList<>());
                break;
            case TRACKPOINT:
                currTrackPoint = new TrackPoint();
                break;
            case TIME:
                elementValue = new StringBuilder();
                break;
            case LATITUDE:
                elementValue = new StringBuilder();
                break;
            case LONGITUDE:
                elementValue = new StringBuilder();
                break;
            case ALTITUDE:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case ACTIVITY:
                break;
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
                currLap.setDistanceMeters(Double.parseDouble(elementValue.toString()));
                break;
            case MAXIMUMSPEED:
                currLap.setMaxSpeed(Double.parseDouble(elementValue.toString()));
                break;
            case CALORIES:
                currLap.setCalories(Double.parseDouble(elementValue.toString()));
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
                } catch (Exception e) {
                    e.printStackTrace();
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
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) elementValue = new StringBuilder();
        else elementValue.append(ch, start, length);
    }

    public Activity getActivity() {
        return a;
    }
}