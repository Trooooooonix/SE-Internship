package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tracks.Activity;
import tracks.Lap;
import tracks.Track;
import tracks.TrackPoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
All values which are not included in gpx files but in tcx files are set to 0
 */

public class GPXActivityHandler extends DefaultHandler {
    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String TRKSEG = "trkseg";
    private static final String TRKPT = "trkpt";
    private static final String ELE = "ele";
    private static final String TIME = "time";

    private Activity a;
    private Lap currLap;
    private Track currTrack;
    private TrackPoint currTrackPoint;
    private StringBuilder elementValue;

    /*
    //please DO NOT use this trash code
    //used for formatting those ("01.04.2021  7:31 pm") formats into a classic LocalDateTime format
    //used in gpx for Value in Tag <desc>VALUE</desc>
    public static LocalDateTime format(String dateTime) {
        String date = dateTime.substring(0, 10);
        String time = dateTime.substring(11, 16);
        if (dateTime.contains("pm")) {
            int hours = (int) Double.parseDouble(dateTime.substring(11, 13)) + 12;
            time = hours + ":" + time.substring(3);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm");
        return LocalDateTime.parse(date + "T" + time, dtf);
    }*/

    @Override
    public void startDocument(){
        a = new Activity();
        a.setSport("no Sport identified");
        a.setLaps(new ArrayList<>());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        switch (qName) {
            case TRKSEG:
                currLap = new Lap();
                currLap.setTracks(new ArrayList<>());
                currTrack = new Track();
                currTrack.setTrackPoints(new ArrayList<>());
                //TODO: getStartTime formatted and add it

                //TODO: set default values for not included tags

                break;
            case TRKPT:
                currTrackPoint = new TrackPoint();
                currTrackPoint.setLatitude(Double.parseDouble(attributes.getValue(0)));
                currTrackPoint.setLongitude(Double.parseDouble(attributes.getValue(1)));
                break;
            case DESC:
            case NAME:
            case ELE:
            case TIME:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        switch (qName) {
            case NAME:
                a.setId(elementValue.toString());
                break;
            case TRKSEG:
                currLap.getTracks().add(currTrack);
                a.getLaps().add(currLap);
                break;
            case TRKPT:
                currTrack.addTrackPoint(currTrackPoint);
                break;
            case ELE:
                currTrackPoint.setAltitude(Double.parseDouble(elementValue.toString()));
            case TIME:
                try {
                    currTrackPoint.setTime(LocalDateTime.parse(elementValue.substring(0, elementValue.length() - 1)));
                } catch (Exception e) {
                    System.out.println("Wrong DateTime format");
                }
            case DESC:
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy   HH:mm'pm'");
                currLap.setStartTime(LocalDateTime.parse(elementValue, dtf));
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (elementValue == null) elementValue = new StringBuilder();
        else elementValue.append(ch, start, length);
    }

    public Activity getActivity() {
        return a;
    }
}
