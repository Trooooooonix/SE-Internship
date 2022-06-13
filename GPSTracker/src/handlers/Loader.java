package handlers;

import gui.LoadingFrame;
import gui.GpsTrackerGUI;
import gui.LoadingFrame;
import handlers.ActivityHandler;
import handlers.GPXActivityHandler;
import tracks.Activity;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.swing.text.IconView;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Loader {

    public static void initLoading() throws IOException, ParserConfigurationException, SAXException {
        //loading window, so user does not open the app again
        LoadingFrame lf = new LoadingFrame("Loading");
        lf.setLocationRelativeTo(null);
        lf.pack();
        lf.setVisible(true);
        String rootPath = readProperties();
        List<Path> filePaths = getFilePaths(rootPath);
        List<Activity> aList = loadData(filePaths);
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setLocationRelativeTo(null);
        ui.setIconImage(new ImageIcon("icon.png").getImage());
        lf.setVisible(false);
        ui.setVisible(true);
    }

    public static String readProperties() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        PropertiesHandler ph = new PropertiesHandler();
        saxParser.parse("GPSTracker/src/properties.xml", ph);
        return ph.getFilePath();
    }

    public static List<Activity> loadData(List<Path> filePathList) throws IOException, SAXException, ParserConfigurationException {
        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (p.toString().endsWith("tcx")) {
                ActivityHandler ah = new ActivityHandler();
                saxParser.parse(p.toString(), ah);
                aList.add(ah.getActivity());
            //TODO GPX Handler
            }else if (p.toString().endsWith("gpx")){
                //GPXActivityHandler gh = new GPXActivityHandler();
                //saxParser.parse(p.toString(),gh);
                //aList.add((gh.getActivity()));
            }
        }
        return aList;
    }

    public static List<Path> getFilePaths(String filePath) throws IOException{
        List<Path> filePathList;
        try (Stream<Path> paths = Files.walk(Paths.get(filePath))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
        }
        return filePathList;
    }
}
