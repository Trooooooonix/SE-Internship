package gui;

import javax.swing.*;

public class GpsTrackerGUI extends JFrame {

    private JPanel panel1;

    public GpsTrackerGUI(String title) {
        super(title);
        this.initiateMenuBar(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
    }

    public void initiateMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        //adding Menus to the Menubar
        JMenu navigationMenu = new JMenu("File");
        menuBar.add(navigationMenu);
        JMenuItem option = new JMenuItem("Option");
        navigationMenu.add(option);
        JMenuItem refresh = new JMenuItem("Refresh");
        navigationMenu.add(refresh);


        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);


        JMenu groupMenu = new JMenu("Group by");
        menuBar.add(groupMenu);
        JMenuItem year = new JMenuItem("Year");
        groupMenu.add(year);
        JMenuItem month = new JMenuItem("Month");
        groupMenu.add(month);
        JMenuItem day = new JMenuItem("Day");
        groupMenu.add(day);

        /*
            Default: checked
            if checked column is shown
         */

        JMenu columnMenu = new JMenu("Columns");
        menuBar.add(columnMenu);
        JCheckBoxMenuItem name = new JCheckBoxMenuItem("Name");
        name.setSelected(true);
        columnMenu.add(name);
        JCheckBoxMenuItem date = new JCheckBoxMenuItem("Date");
        date.setSelected(true);
        columnMenu.add(date);
        JCheckBoxMenuItem startTime = new JCheckBoxMenuItem("Start");
        startTime.setSelected(true);
        columnMenu.add(startTime);
        JCheckBoxMenuItem distance = new JCheckBoxMenuItem("Distance");
        distance.setSelected(true);
        columnMenu.add(distance);
        JCheckBoxMenuItem pace = new JCheckBoxMenuItem("Pace");
        pace.setSelected(true);
        columnMenu.add(pace);

        JCheckBoxMenuItem averageBpm = new JCheckBoxMenuItem("Average Bpm");
        averageBpm.setSelected(true);
        columnMenu.add(averageBpm);
        JCheckBoxMenuItem maxBpm = new JCheckBoxMenuItem("max Bpm");
        maxBpm.setSelected(true);
        columnMenu.add(maxBpm);
        JCheckBoxMenuItem heightLvl = new JCheckBoxMenuItem("height");
        heightLvl.setSelected(true);
        columnMenu.add(heightLvl);

        JMenu track = new JMenu("Track");
        menuBar.add(track);
        JMenuItem cycling = new JMenuItem("Cycling");
        track.add(cycling);
        JMenuItem driving = new JMenuItem("Driving");
        track.add(driving);
        JMenuItem flying = new JMenuItem("Flying");
        track.add(flying);
        JMenuItem hiking = new JMenuItem("Hiking");
        track.add(hiking);
        JMenuItem running = new JMenuItem("Running");
        track.add(running);
        JMenuItem skiing = new JMenuItem("Skiing");
        track.add(skiing);


        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
    }
}
