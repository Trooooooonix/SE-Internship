package gui;


import parser.GpxParser;

public class Main {

    public static void main(String[] args) {
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer");
        ui.setVisible(true);
        GpxParser asd = new GpxParser();
        asd.reader();
    }
}