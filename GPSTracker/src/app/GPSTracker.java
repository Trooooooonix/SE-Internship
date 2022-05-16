package app;


import gui.GpsTrackerGUI;
import gui.LoadingFrame;
import handlers.ActivityHandler;
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
import java.util.stream.Stream;

public class GPSTracker {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        /*
        JFrame loading = new JFrame();
        loading.setLocationRelativeTo(null);
        JPanel jp = new JPanel();

        loading.add(jp, BorderLayout.CENTER);

        JLabel loadText = new JLabel("Loading...", SwingConstants.CENTER);
        jp.add(loadText, BorderLayout.CENTER);
        loadText.setFont(new Font("Serif", Font.BOLD, 35));
        loadText.setVisible(true);
        ImageIcon icon = new ImageIcon("jetbrains://idea/navigate/reference?project=SE-Internship&path=RunningMan.gif");



        loading.setUndecorated(true);
        loading.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        loading.setSize(300,75);
        loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loading.setLocationRelativeTo(null);
        loading.setVisible(true);
        */

        LoadingFrame lf = new LoadingFrame("Loading");
        lf.setSize(350, 450);
        lf.setLocationRelativeTo(null);
        lf.setVisible(true);


        List<Path> filePathList;
        try (Stream<Path> paths = Files.walk(Paths.get("GPSTracker/tcxFiles/testdata"))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
        }

        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            if (p.toString().endsWith("tcx")) {
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                ActivityHandler ah = new ActivityHandler();
                saxParser.parse(p.toString(), ah);
                aList.add(ah.getActivity());
            }
        }

        //checkParser(aList);
        lf.setVisible(false);
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setLocationRelativeTo(null);
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
            System.out.println(acadia.getLaps().get(i).getDistanceMeters());
        }
        /*
        for (Activity a : list) {
            System.out.println("Sport: " + a.getSport());
            System.out.println("Id: " + a.getId());
            System.out.println("Number of laps: " + a.getLaps().size());
        }*/
    }
}