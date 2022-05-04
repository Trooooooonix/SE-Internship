package com.se.app;


import com.se.gui.GpsTrackerGUI;
import com.se.handlers.ActivityHandler;
import com.se.tracks.Activity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GPSTracker {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer");
        ui.setVisible(true);

        List<Path> filePathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("D:/testdata"))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
        }

        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ActivityHandler ah = new ActivityHandler();
            saxParser.parse(p.toString(), ah);
            aList.add(ah.getActivity());
        }

        System.out.println(aList.size());
        Activity eins = aList.get(0);
        System.out.println(eins);

    }
}