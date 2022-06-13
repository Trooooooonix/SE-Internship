package app;

import handlers.Loader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class GPSTracker {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        Loader.initLoading();
        //DirectoryChooser.createWindow();
    }

    /*public static void checkParser(List<Activity> list) {
        System.out.println("Loaded files: " + list.size());

        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Activity acadia = list.get(9);
        System.out.println("arcadia.tcx");
        System.out.println("Name: " + acadia.getId());
        System.out.println("Date: " + acadia.getLaps().get(0).getStartTime().format(viewDateFormatter));
        System.out.println("Start Time: " + acadia.getLaps().get(0).getStartTime().format(viewStartTimeFormatter));
        for (int i = 0; i < acadia.getLaps().size(); i++) {
            System.out.println("DistanceMeters:" + acadia.getLaps().get(i).getDistanceMeters());
            System.out.println("AVGBPM: " + acadia.getLaps().get(i).getAverageBPM());
            System.out.println("MAXBPM: " + acadia.getLaps().get(i).getMaxBPM());

        }

        for (Activity a : list) {
            System.out.println("Sport: " + a.getSport());
            System.out.println("Id: " + a.getId());
            System.out.println("Number of laps: " + a.getLaps().size());
        }
    }*/
}