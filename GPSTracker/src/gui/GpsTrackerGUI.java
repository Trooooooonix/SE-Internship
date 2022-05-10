package gui;

import tracks.Activity;
import tracks.Lap;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static gui.BarChart.createChart;

public class GpsTrackerGUI extends JFrame {

    private JPanel rootPanel;
    private JTable trackTable;
    private JTable segmentTable;
    private JPanel chartPanel;
    private JPanel Tracks;
    public int row = 0;
    public JCheckBoxMenuItem startTime;
    public JCheckBoxMenuItem pace;
    public JCheckBoxMenuItem averageBpm;
    public JCheckBoxMenuItem maxBpm;
    public JCheckBoxMenuItem heightLvl;


    public GpsTrackerGUI(String title, List<Activity> aList) {
        super(title);
        initiateMenuBar(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(rootPanel);
        this.pack();
        createTrackTable(aList);
        createSegmentTable(aList);
        createBarChart(aList);

        trackTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = trackTable.getSelectedRow();
                createSegmentTable(aList);
                createBarChart(aList);
            }
        });
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
        startTime = new JCheckBoxMenuItem("Start");
        startTime.setSelected(true);
        columnMenu.add(startTime);
        pace = new JCheckBoxMenuItem("Pace");
        pace.setSelected(true);
        columnMenu.add(pace);
        averageBpm = new JCheckBoxMenuItem("avg. Bpm");
        averageBpm.setSelected(true);
        columnMenu.add(averageBpm);
        maxBpm = new JCheckBoxMenuItem("max. Bpm");
        maxBpm.setSelected(true);
        columnMenu.add(maxBpm);
        heightLvl = new JCheckBoxMenuItem("Altitude");
        heightLvl.setSelected(true);
        columnMenu.add(heightLvl);

        JMenu track = new JMenu("Track");
        menuBar.add(track);
        JMenuItem allTypes = new JMenuItem("all Types");
        track.add(allTypes);
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

    private void createTrackTable(List<Activity> aList) {

        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Object[][] data = new Object[aList.size()][9];
        int counter = 0;
        double activityDistanceMeters;
        double activityTotalTimeSeconds;

        for (Activity a : aList) {
            data[counter][0] = a.getId();
            data[counter][1] = a.getLaps().get(0).getStartTime().format(viewDateFormatter);
            data[counter][2] = a.getLaps().get(0).getStartTime().format(viewStartTimeFormatter);
            activityDistanceMeters = a.getActivityDistanceMeters();
            data[counter][3] = activityDistanceMeters;
            activityTotalTimeSeconds = a.getActivityTotalTimeSeconds();
            data[counter][4] = a.getTotalTimeHHmmSS(activityTotalTimeSeconds);
            data[counter][5] = Math.round((activityDistanceMeters / activityTotalTimeSeconds) * 100.00) / 100.00;
            data[counter][6] = a.getId();
            data[counter][7] = a.getId();
            data[counter][8] = a.getActivityTotalAltitude();
            counter++;
        }


        trackTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        trackTable.setRowSelectionAllowed(true);
        trackTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Date", "Start", "Distance", "Time", "Pace", "avg. BPM", "max. BPM", "Altitude"} // String ändern wenn Spalten ausgeblendet werden sollen
        ));
        TableColumnModel columns = trackTable.getColumnModel();
        columns.getColumn(0).setMinWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        columns.getColumn(4).setCellRenderer(centerRenderer);
        columns.getColumn(5).setCellRenderer(centerRenderer);
        columns.getColumn(6).setCellRenderer(centerRenderer);
        columns.getColumn(7).setCellRenderer(centerRenderer);
        columns.getColumn(8).setCellRenderer(centerRenderer);


     /*   if (startTime.isSelected()) {
            trackTable.getColumnModel().getColumn(0).setMinWidth(0);
            trackTable.getColumnModel().getColumn(0).setMaxWidth(0);
        } else {
            trackTable.getColumnModel().getColumn(0).setMinWidth(0);
            trackTable.getColumnModel().getColumn(0).setMaxWidth(999);
        }     */
    }




    private void createSegmentTable(List<Activity> aList){

        segmentTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        segmentTable.setRowSelectionAllowed(true);

        Object [][] data = new Object [aList.get(row).getLaps().size()][6];
        int counter =0;

        for (Lap l : aList.get(row).getLaps()) {
            data [counter][0] = counter+1;
            data [counter][1] = Math.round(l.getDistanceMeters()*100.00)/100.00;
            data [counter][2] = l.getTotalTimeHHmmSS(aList.get(row).getLaps().get(counter).getTotalTimeSeconds());
            data [counter][3] = l.getLapTotalAltitude();
            data [counter][4] = counter+1;
            data [counter][5] = counter+1;
            counter++;
        }

        segmentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Lap", "Distance", "Time", "Altitude", "avg. BPM", "max. BPM"}
        ));
        TableColumnModel columns = segmentTable.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        columns.getColumn(4).setCellRenderer(centerRenderer);
        columns.getColumn(5).setCellRenderer(centerRenderer);
    }

    private void createBarChart(List<Activity> aList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int counter = 1;
        Activity currActivity = aList.get(row);
        for (Lap l : currActivity.getLaps()) {
            double activityDistanceMeters = l.getDistanceMeters();
            double activityTotalTimeSeconds = l.getTotalTimeSeconds();
            dataset.setValue(Math.round((activityDistanceMeters/activityTotalTimeSeconds)*100.00)/100.00, "", "Lap "+ counter++);
        }

        ChartPanel chPanel = new ChartPanel(createChart(dataset));
        chPanel.setPreferredSize(new Dimension(800, 250)); //size according to my window
        chPanel.setMouseWheelEnabled(true);
        chPanel.setFillZoomRectangle(true);

        chartPanel.removeAll();
        chartPanel.add(chPanel);
        chartPanel.repaint();
        chartPanel.updateUI();
    }
}
