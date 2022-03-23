package gui;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.resources.JFreeChartResources;

public class Main {

    public static void main(String[] args) {
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer");
        ui.setVisible(true);
    }
}