package gui;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        /*
            creating the window for the GUI
            setting a windowSize and a default operation to close upon clicking the "X" in the top-right
         */
        JFrame window = new JFrame("GPS-Tracker v420.69");
        window.setSize(1920,1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.LIGHT_GRAY);

        JFrame optionWindow = new JFrame("Options");
        optionWindow.setSize(1220,980);
        optionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionWindow.setBackground(Color.LIGHT_GRAY);

        /*
            adding a button to go back from the option-window to the main-window
         */
        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        doneButton.setSize(200, 200);
        optionWindow.add(doneButton, BorderLayout.SOUTH);
        doneButton.addActionListener(a -> {
            optionWindow.setVisible(false);
            window.setVisible(true);
        });

        /*
            adding the menubar to the main WINDOW (variableName = window)
         */
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        /*
            adding Menus to the Menubar
         */
        JMenu navigationMenu = new JMenu("File");
        menuBar.add(navigationMenu);
        JMenuItem option = new JMenuItem("Option");
        navigationMenu.add(option);
        JMenuItem refresh = new JMenuItem("Refresh");
        navigationMenu.add(refresh);

        /*
            adding actionListeners to react on click
         */
        refresh.addActionListener(a -> window.repaint());

        option.addActionListener(a -> {
            window.setVisible(false);
            optionWindow.setVisible(true);
        });





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

        /*
            adding internal Frames to show specific Data
            Size is not working right now, should be in the right top of the window-frame and show the Overview

            JInternalFrame internalFrame = new JInternalFrame("Overview", true, false, false, false);
            internalFrame.setSize(200, 200);
            internalFrame.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
            internalFrame.setVisible(true);
            window.add(internalFrame, BorderLayout.NORTH);
        */

        window.setVisible(true);
    }
}
