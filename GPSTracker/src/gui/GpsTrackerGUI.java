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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static gui.BarChart.createChart;

public class GpsTrackerGUI extends JFrame {
    public String yAxis;
    private JPanel rootPanel;
    private JTable trackTable;
    private JTable segmentTable;
    private JPanel chartPanel;
    private JPanel Tracks;      //needed
    private JLabel nameLabel;
    public int trackRow = 0;
    public int segmentColumn = 1;
    public JCheckBoxMenuItem startTime;
    public JCheckBoxMenuItem pace;
    public JCheckBoxMenuItem averageBpm;
    public JCheckBoxMenuItem maxBpm;
    public JCheckBoxMenuItem heightLvl;
    public int tableWidth = 97;
    NumberFormat paceFormatter = new DecimalFormat("#0.00");
    NumberFormat distanceFormatter = new DecimalFormat("#0,000");


    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!startTime.isSelected()) {
                trackTable.getColumnModel().getColumn(2).setMinWidth(0);
                trackTable.getColumnModel().getColumn(2).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(2).setMaxWidth(0);
                System.out.println("JMenuItemAction: startTime deselected - Width:" + trackTable.getColumnModel().getColumn(2).getWidth());
            } else{
                trackTable.getColumnModel().getColumn(2).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(2).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(2).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: startTime selected - Width:" + trackTable.getColumnModel().getColumn(2).getWidth());
            }

            if (!pace.isSelected()) {
                trackTable.getColumnModel().getColumn(5).setMinWidth(0);
                trackTable.getColumnModel().getColumn(5).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(5).setMaxWidth(0);
                System.out.println("JMenuItemAction: pace deselected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            } else{
                trackTable.getColumnModel().getColumn(5).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(5).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(5).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: pace selected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            }

            if (!averageBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(6).setMinWidth(0);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(0);
                System.out.println("JMenuItemAction: averageBpm deselected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            } else{
                trackTable.getColumnModel().getColumn(6).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: averageBpm selected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            }

            if (!maxBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(7).setMinWidth(0);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(0);
                System.out.println("JMenuItemAction: maxBpm deselected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            } else{
                trackTable.getColumnModel().getColumn(7).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: maxBpm selected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            }

            if (!heightLvl.isSelected()) {
                trackTable.getColumnModel().getColumn(8).setMinWidth(0);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(0);
                System.out.println("JMenuItemAction: heightLvl deselected - Width:" + trackTable.getColumnModel().getColumn(8).getWidth());
            } else{
                trackTable.getColumnModel().getColumn(8).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: heightLvl selected - Width:" + trackTable.getColumnModel().getColumn(8).getWidth());
            }
        }
    }

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
                trackRow = trackTable.getSelectedRow();
                System.out.print(trackRow);
                createSegmentTable(aList);
                createBarChart(aList);
            }
        });

        segmentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                segmentColumn = segmentTable.getSelectedColumn();
                System.out.print(segmentColumn);
                createBarChart(aList);
            }
        });

    }

    public void initiateMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        //adding Menus to the Menubar
    //    JMenu navigationMenu = new JMenu("File");
    //    menuBar.add(navigationMenu);
    //    JMenuItem option = new JMenuItem("Option");
    //    navigationMenu.add(option);
    //    JMenuItem refresh = new JMenuItem("Refresh");
    //    navigationMenu.add(refresh);
    //    JMenu viewMenu = new JMenu("View");
    //    menuBar.add(viewMenu);
        JMenu groupMenu = new JMenu("Group by");
        menuBar.add(groupMenu);
        JMenuItem year = new JMenuItem("Year");
        groupMenu.add(year);
        JMenuItem month = new JMenuItem("Month");
        groupMenu.add(month);
        JMenuItem day = new JMenuItem("Day");
        groupMenu.add(day);


        JMenu columnMenu = new JMenu("Columns");
        menuBar.add(columnMenu);
        startTime = new JCheckBoxMenuItem("Start");
        startTime.setSelected(true);
        startTime.addActionListener(new MenuActionListener());
        columnMenu.add(startTime);
        pace = new JCheckBoxMenuItem("Pace");
        pace.setSelected(true);
        pace.addActionListener(new MenuActionListener());
        columnMenu.add(pace);
        averageBpm = new JCheckBoxMenuItem("avg. Bpm");
        averageBpm.setSelected(true);
        averageBpm.addActionListener(new MenuActionListener());
        columnMenu.add(averageBpm);
        maxBpm = new JCheckBoxMenuItem("max. Bpm");
        maxBpm.setSelected(true);
        maxBpm.addActionListener(new MenuActionListener());
        columnMenu.add(maxBpm);
        heightLvl = new JCheckBoxMenuItem("Altitude");
        heightLvl.setSelected(true);
        heightLvl.addActionListener(new MenuActionListener());
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


    //    JMenu helpMenu = new JMenu("Help");
    //    menuBar.add(helpMenu);
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
            activityDistanceMeters = Math.round(a.getActivityDistanceMeters());
            if (activityDistanceMeters >= 1000) data[counter][3] = distanceFormatter.format(activityDistanceMeters);
            else data[counter][3] = activityDistanceMeters;
            activityTotalTimeSeconds = a.getActivityTotalTimeSeconds();
            data[counter][4] = a.getTotalTimeHHmmSS(activityTotalTimeSeconds);
            data[counter][5] = paceFormatter.format(activityDistanceMeters / activityTotalTimeSeconds);
            data[counter][6] = Math.round(a.getAvgBPM());
            data[counter][7] = Math.round(a.getMaxBPM());
            data[counter][8] = Math.round(a.getActivityTotalAltitude());
            counter++;
        }

        trackTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        trackTable.setRowSelectionAllowed(true);
        trackTable.setColumnSelectionAllowed(false);
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
    }

    private void createSegmentTable(List<Activity> aList){


        Object [][] data = new Object [aList.get(trackRow).getLaps().size()][6];
        int counter =0;

        double prevDistanceMeters = 0.0;
        for (Lap l : aList.get(trackRow).getLaps()) {
            data [counter][0] = counter+1;
            if ((l.getDistanceMeters()-prevDistanceMeters) >= 1000) data [counter][1] = distanceFormatter.format(Math.round((l.getDistanceMeters()-prevDistanceMeters)));
            else data [counter][1] = Math.round((l.getDistanceMeters()-prevDistanceMeters));
            data [counter][2] = l.getTotalTimeHHmmSS(aList.get(trackRow).getLaps().get(counter).getTotalTimeSeconds());
            data [counter][3] = Math.round(l.getLapTotalAltitude());
            data [counter][4] = Math.round(l.getAverageBPM());
            data [counter][5] = Math.round(l.getMaxBPM());
            counter++;
            prevDistanceMeters = Math.round(l.getDistanceMeters());
        }
        segmentTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        segmentTable.setRowSelectionAllowed(false);
        segmentTable.setColumnSelectionAllowed(true);
        segmentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Segment", "Distance", "Time", "Altitude", "avg. BPM", "max. BPM"}
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
        DefaultCategoryDataset distanceDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset timeDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset altitudeDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset avgBpmDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset maxBpmDataset = new DefaultCategoryDataset();

        int counter = 1;
        double prevDistanceMeters = 0.0;
        Activity currActivity = aList.get(trackRow);
        for (Lap l : currActivity.getLaps()) {
            distanceDataset.setValue((Number) (l.getDistanceMeters()- prevDistanceMeters), "meter", counter);
            timeDataset.setValue((Number) l.getTotalTimeSeconds(), "seconds",  counter);
            altitudeDataset.setValue((Number) segmentTable.getModel().getValueAt(counter-1,3), "meter", counter);
            avgBpmDataset.setValue((Number) segmentTable.getModel().getValueAt(counter-1,4), "beats/min", counter);
            maxBpmDataset.setValue((Number) segmentTable.getModel().getValueAt(counter-1,5), "beats/min", counter);
            prevDistanceMeters = Math.round(l.getDistanceMeters());
            counter++;
        }
        DefaultCategoryDataset dataset = distanceDataset;

        switch (segmentColumn) {
            case 1 :
                dataset = distanceDataset;
                yAxis = "Distance  [m]";
                nameLabel.setText("Distance");
                break;
            case 2 :
                dataset = timeDataset;
                yAxis = "Time  [sec]";
                nameLabel.setText("Time");
                break;
            case 3 :
                dataset = altitudeDataset;
                yAxis = "Altitude  [m]";
                nameLabel.setText("Altitude");
                break;
            case 4 :
                dataset = avgBpmDataset;
                yAxis = "Average BPM  [beats/min]";
                nameLabel.setText("Average BPM");
                break;
            case 5 :
                dataset = maxBpmDataset;
                yAxis = "Max. BPM  [beats/min]";
                nameLabel.setText("Max. BPM");
                break;
        }

        ChartPanel chPanel = new ChartPanel(createChart(dataset, yAxis));
        chPanel.setPreferredSize(new Dimension(800, 300)); //size according to my window
        chPanel.setMouseWheelEnabled(true);
        chPanel.setFillZoomRectangle(true);

        chartPanel.removeAll();
        chartPanel.add(chPanel);
        chartPanel.repaint();
        chartPanel.updateUI();
    }
}