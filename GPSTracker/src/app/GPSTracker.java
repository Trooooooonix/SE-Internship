package app;


import gui.GpsTrackerGUI;
import handlers.ActivityHandler;
import handlers.GPXActivityHandler;
import tracks.Activity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GPSTracker {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        List<Path> filePathList;
        try (Stream<Path> paths = Files.walk(Paths.get("GPSTracker/tcxFiles/testdata"))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
        }

        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (p.toString().endsWith("tcx")) {
                ActivityHandler ah = new ActivityHandler();
                saxParser.parse(p.toString(), ah);
                aList.add(ah.getActivity());
            }else if (p.toString().endsWith("gpx")){
                GPXActivityHandler gh = new GPXActivityHandler();
                saxParser.parse(p.toString(),gh);
                aList.add((gh.getActivity()));
            }
        }
        checkParser(aList);
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setVisible(true);
    }

    public static void checkParser(List<Activity> list){
        System.out.println("Loaded files: " + list.size());

        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Activity acadia = list.get(0);
        System.out.println("acadia.tcx");
        System.out.println("Name: " + acadia.getId());
        System.out.println("Date: " + acadia.getLaps().get(0).getStartTime().format(viewDateFormatter));
        System.out.println("Start Time: "+ acadia.getLaps().get(0).getStartTime().format(viewStartTimeFormatter));
        for (int i = 0; i < acadia.getLaps().size(); i++) {
            System.out.println("DistanceMeters:" + acadia.getLaps().get(i).getDistanceMeters());
            System.out.println("AVGBPM: " + acadia.getLaps().get(i).getAverageBPM());
            System.out.println("MAXBPM: "+ acadia.getLaps().get(i).getMaxBPM());

        }
        /*
        for (Activity a : list) {
            System.out.println("Sport: " + a.getSport());
            System.out.println("Id: " + a.getId());
            System.out.println("Number of laps: " + a.getLaps().size());
        }*/
    }
}