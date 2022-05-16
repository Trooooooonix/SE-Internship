package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tracks.Activity;
import tracks.Lap;

import java.util.ArrayList;

public class GPXActivityHandler extends DefaultHandler {
    private static final String TRK = "trk";
    private static final String NAME = "name";
    private static final String TRKSEG = "trkseg";
    private static final String TRKPT = "trkpt";
    private static final String ELE = "ele";
    private static final String TIME = "time";

    private Activity a;
    private Lap currLap;
    private StringBuilder elementValue;


    @Override
    public void startDocument() throws SAXException {
        a = new Activity();
        a.setSport(null);
        a.setLaps(new ArrayList<>());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case NAME:
                elementValue = new StringBuilder();
                break;
            case TRKSEG:
                currLap = new Lap();
                currLap.setTracks(new ArrayList<>());
                break;
            case TRKPT:
                break;
            case ELE:
                break;
            case TIME:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case NAME:
                a.setId(elementValue.toString());
                break;
            case TRKSEG:
                a.getLaps().add(currLap);
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
