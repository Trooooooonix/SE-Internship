package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import tracks.Activity;
import tracks.Lap;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static gui.GpsTrackerGUI.BarChart.createChart;

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
    public DefaultCategoryDataset dataset;
    public JCheckBoxMenuItem startTime, pace, averageBpm, maxBpm, heightLvl;
    public JMenuItem year, month, activity;
    public JMenuItem running, cycling, driving, allTypes, flying, hiking, skiing;
    public int tableWidth = 97;
    public String sportType = "all";
    public String groupBy = "activity";


    NumberFormat paceFormatter = new DecimalFormat("#0.00");
    NumberFormat distanceFormatter = new DecimalFormat("#0,000");

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!startTime.isSelected()) {
                trackTable.getColumnModel().getColumn(2).setMinWidth(0);
                trackTable.getColumnModel().getColumn(2).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(2).setMaxWidth(0);
                System.out.println("JMenuItemAction: startTime deselected - Width:" + trackTable.getColumnModel().getColumn(2).getWidth());
            } else {
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
            } else {
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
            } else {
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
            } else {
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
            } else {
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


        allTypes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("All Types selected");
                sportType = "all";
                updateGUI(aList);
            }
        });

        cycling.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Cycling selected");
                sportType = "Cycling";
                updateGUI(aList);
            }
        });

        driving.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Driving selected");
                sportType = "Driving";
                updateGUI(aList);
            }
        });

        flying.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Flying selected");
                sportType = "Flying";
                updateGUI(aList);
            }
        });

        hiking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Hiking selected");
                sportType = "Hiking";
                updateGUI(aList);
            }
        });

        running.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Running selected");
                sportType = "Running";
                updateGUI(aList);
            }
        });

        skiing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Skiing selected");
                sportType = "Skiing";
                updateGUI(aList);
            }
        });

        year.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Group by Year selected");
                groupBy = "Year";
                getGroupOfDate(aList);
                updateGUI(aList);
            }
        });

        month.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Group by Month selected");
                groupBy = "Month";
                updateGUI(aList);
            }
        });

        activity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.out.println("Group by Activity selected");
                groupBy = "Activity";
                updateGUI(aList);
            }
        });
    }

    public void updateGUI(List<Activity> aList) {
        createTrackTable(aList);
        // Gruppieren nach Zeitraum
        trackRow = 0;
        createSegmentTable(getListOfSport(aList, sportType));
        segmentColumn = 1;
        createBarChart(getListOfSport(aList, sportType));
    }

    public void initiateMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        JMenu groupMenu = new JMenu("Group by");
        menuBar.add(groupMenu);
        year = new JMenuItem("Year");
        groupMenu.add(year);
        month = new JMenuItem("Month");
        groupMenu.add(month);
        activity = new JMenuItem("Activity");
        groupMenu.add(activity);


        JMenu columnMenu = new JMenu("Hide Columns");
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

        JMenu track = new JMenu("Filter by Sport");
        menuBar.add(track);
        allTypes = new JMenuItem("all Types");
        track.add(allTypes);
        cycling = new JMenuItem("Cycling");
        track.add(cycling);
        driving = new JMenuItem("Driving");
        track.add(driving);
        flying = new JMenuItem("Flying");
        track.add(flying);
        hiking = new JMenuItem("Hiking");
        track.add(hiking);
        running = new JMenuItem("Running");
        track.add(running);
        skiing = new JMenuItem("Skiing");
        track.add(skiing);


        //    JMenu helpMenu = new JMenu("Help");
        //    menuBar.add(helpMenu);
    }

    private void createTrackTable(List<Activity> aList) {

        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Object[][] data = new Object[getSizeOfSportList(aList, sportType)][9];
        int counter = 0;
        double activityDistanceMeters;
        double activityTotalTimeSeconds;

        for (Activity a : aList) {
            if (a.getSport().equals(sportType) || sportType.equals("all")) {
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
        }

        trackTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        trackTable.setRowSelectionAllowed(true);
        trackTable.setColumnSelectionAllowed(false);
        trackTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Date", "Start", "Distance", "Time", "Pace", "avg. BPM", "max. BPM", "Altitude"}
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

        trackTable.setRowSelectionInterval(0, 0);
        trackRow = 0;

        trackTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    trackRow = trackTable.getSelectedRow();
                    createSegmentTable(aList);
                    createBarChart(aList);
                }
            }
        });
    }

    private void createSegmentTable(List<Activity> aList) {


        Object[][] data = new Object[aList.get(trackRow).getLaps().size()][7];
        int counter = 0;

        double prevDistanceMeters = 0.0;
        for (Lap l : aList.get(trackRow).getLaps()) {
            data[counter][0] = counter + 1;
            if ((l.getDistanceMeters() - prevDistanceMeters) >= 1000)
                data[counter][1] = distanceFormatter.format(Math.round((l.getDistanceMeters() - prevDistanceMeters)));
            else data[counter][1] = Math.round((l.getDistanceMeters() - prevDistanceMeters));
            data[counter][2] = l.getTotalTimeHHmmSS(aList.get(trackRow).getLaps().get(counter).getTotalTimeSeconds());
            data[counter][3] = Math.round(((l.getTotalTimeSeconds() / 60) / ((l.getDistanceMeters() - prevDistanceMeters) / 1000)) * 100.0) / 100.0;
            data[counter][4] = Math.round(l.getLapTotalAltitude());
            data[counter][5] = Math.round(l.getAverageBPM());
            data[counter][6] = Math.round(l.getMaxBPM());
            counter++;
            prevDistanceMeters = Math.round(l.getDistanceMeters());
        }
        segmentTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        segmentTable.setRowSelectionAllowed(false);
        segmentTable.setColumnSelectionAllowed(true);
        segmentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Segment", "Distance", "Time", "Pace", "Altitude", "avg. BPM", "max. BPM"}
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
        columns.getColumn(6).setCellRenderer(centerRenderer);
        segmentColumn = 1;
        segmentTable.setColumnSelectionInterval(segmentColumn, segmentColumn);

        segmentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    segmentColumn = segmentTable.getSelectedColumn();
                    createBarChart(aList);
                }
            }
        });
    }

    private void createBarChart(List<Activity> aList) {
        DefaultCategoryDataset distanceDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset timeDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset paceDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset altitudeDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset avgBpmDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset maxBpmDataset = new DefaultCategoryDataset();

        int counter = 1;
        double prevDistanceMeters = 0.0;
        Activity currActivity = aList.get(trackRow);
        for (Lap l : currActivity.getLaps()) {
            distanceDataset.setValue((Number) (l.getDistanceMeters() - prevDistanceMeters), "meter", counter);
            timeDataset.setValue((Number) (l.getTotalTimeSeconds() / 60), "minutes", counter);
            paceDataset.setValue((Number) ((l.getTotalTimeSeconds() / 60) / ((l.getDistanceMeters() - prevDistanceMeters) / 1000)), "minutes/km", counter);
            altitudeDataset.setValue((Number) Math.round(l.getLapTotalAltitude()), "meter", counter);
            avgBpmDataset.setValue((Number) Math.round(l.getAverageBPM()), "beats/min", counter);
            maxBpmDataset.setValue((Number) Math.round(l.getMaxBPM()), "beats/min", counter);
            prevDistanceMeters = Math.round(l.getDistanceMeters());
            counter++;
        }

        switch (segmentColumn) {
            case 1:
                dataset = distanceDataset;
                yAxis = "Distance  [m]";
                nameLabel.setText("Distance");
                break;
            case 2:
                dataset = timeDataset;
                yAxis = "Time  [min]";
                nameLabel.setText("Time");
                break;
            case 3:
                dataset = paceDataset;
                yAxis = "Pace  [min/km]";
                nameLabel.setText("Pace");
                break;
            case 4:
                dataset = altitudeDataset;
                yAxis = "Altitude  [m]";
                nameLabel.setText("Altitude");
                break;
            case 5:
                dataset = avgBpmDataset;
                yAxis = "Average BPM  [beats/min]";
                nameLabel.setText("Average BPM");
                break;
            case 6:
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

    public static class BarChart extends ApplicationFrame {

        private static final long serialVersionUID = 1L;

        static {
            ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                    true));
        }

        /**
         * Creates a new instance.
         */
        public BarChart(String title) {
            super(title);
        }

        /**
         * Creates a sample chart.
         */
        public static JFreeChart createChart(CategoryDataset dataset, String yAxis) {
            JFreeChart chart = ChartFactory.createBarChart(
                    " ", "Segment" /* x-axis label*/,
                    yAxis /* y-axis label */, dataset);
            //    chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
            //          + "format (lower bars = better performance)"));
            chart.setBackgroundPaint(Color.white);
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(true);
            chart.getLegend().setFrame(BlockBorder.NONE);
            return chart;
        }

    }

    private int getSizeOfSportList(List<Activity> aList, String sportType) {
        int count = 0;

        if (Objects.equals(sportType, "all")) return aList.size();

        for (Activity a : aList) {
            if (a.getSport().equals(sportType)) {
                count++;
            }
        }
        return count;
    }


    private List<Activity> getListOfSport(List<Activity> aList, String sportType) {

        if (Objects.equals(sportType, "all")) return aList;
        List<Activity> sportsList = new ArrayList<>();
        for (Activity a : aList) {
            if (a.getSport().equals(sportType)) {
                sportsList.add(a);
            }
        }
        return sportsList;
    }


    private List<List> getGroupOfDate(List<Activity> aList) {

        Set<Integer> dates = new HashSet<>();
        for (Activity a : aList) {
            dates.add(a.getLaps().get(0).getStartTime().getYear());
        } // liefert die verschiedenen Jahre aus Liste -> stimmt

        System.out.println(dates.toString());


        List<Activity> group = new ArrayList<Activity>();
        List<List> gList = new ArrayList<>();   // Liste von Gruppen nach Jahren
        for (int d : dates) {
            for (Activity a2 : aList) {
                if (d == (a2.getLaps().get(0).getStartTime().getYear())) {
                    group.add(a2);
                }
            }

            gList.add(group);
            //for (List gL : gList) {
               // for (int i=0; i < group.size(); i++){
                    //System.out.println(d + ": " + gL.get(i).toString());
                  //  System.out.println(d);
                //}
            //}
        }




        return gList;
    }
}