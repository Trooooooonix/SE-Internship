package app;

import handlers.Loader;
import tracks.Activity;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GPSTracker {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Loader.initLoading();
    }

    public static void checkParser(List<Activity> list) {
        System.out.println("Loaded files: " + list.size());

        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Activity acadia = list.get(9);
        System.out.println("acadia.tcx");
        System.out.println("Name: " + acadia.getId());
        System.out.println("Date: " + acadia.getLaps().get(0).getStartTime().format(viewDateFormatter));
        System.out.println("Start Time: " + acadia.getLaps().get(0).getStartTime().format(viewStartTimeFormatter));
        for (int i = 0; i < acadia.getLaps().size(); i++) {
            System.out.println("DistanceMeters:" + acadia.getLaps().get(i).getDistanceMeters());
            System.out.println("AVGBPM: " + acadia.getLaps().get(i).getAverageBPM());
            System.out.println("MAXBPM: " + acadia.getLaps().get(i).getMaxBPM());

        }
        /*
        for (Activity a : list) {
            System.out.println("Sport: " + a.getSport());
            System.out.println("Id: " + a.getId());
            System.out.println("Number of laps: " + a.getLaps().size());
        }*/
    }
}