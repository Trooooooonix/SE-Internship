package gui;


import parser.GpxParser;
import parser.TcxParser;

public class Main {

    public static void main(String[] args) {
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer");
        ui.setVisible(true);
        TcxParser asd = new TcxParser();
        asd.reader();
    }
}