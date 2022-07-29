package gui;

import enums.Period;
import enums.SportType;
import handlers.Formatter;
import handlers.Loader;
import handlers.Logging;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.xml.sax.SAXException;
import tracks.Activity;
import tracks.Lap;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static gui.GpsTrackerGUI.BarChart.createChart;
import static java.util.stream.Collectors.groupingBy;

public class GpsTrackerGUI extends JFrame {
    private static String yAxis;
    private static int trackRow = 0;
    private static int segmentColumn = 1;
    private static DefaultCategoryDataset dataset;
    private static JCheckBoxMenuItem startTime;
    private static JCheckBoxMenuItem pace;
    private static JCheckBoxMenuItem averageBpm;
    private static JCheckBoxMenuItem maxBpm;
    private static JCheckBoxMenuItem heightLvl;
    private static JMenuItem refresh;
    private static JMenuItem location;
    private static JMenuItem year;
    private static JMenuItem month;
    private static JMenuItem activity;
    private static JMenuItem running;
    private static JMenuItem cycling;
    private static JMenuItem driving;
    private static JMenuItem allTypes;
    private static JMenuItem flying;
    private static JMenuItem hiking;
    private static JMenuItem skiing;
    private static final int TRACK_TABLE_WIDTH = 85;
    private static SportType sportType = SportType.all;
    private static Period groupBy = Period.ACTIVITY;

    private JPanel rootPanel;
    private JTable trackTable;
    private JTable segmentTable;
    private JPanel chartPanel;
    private JPanel Tracks;      //needed
    private JLabel nameLabel;
    private JPanel segmentPanel;
    private JPanel barChartPanel;
    private List<Activity> currList;


    public GpsTrackerGUI(String title, List<Activity> aList) {
        super(title);
        initiateMenuBar(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(rootPanel);
        this.pack();
        updateGUI(aList);
    }

    /**
     * updates the View of the GUI
     *
     * @param aList: List of activities
     */
    public void updateGUI(List<Activity> aList) {
        aList.sort(Comparator.comparing(Activity::getDate));
        Collections.reverse(aList);
        initiateMenuBar(this);
        trackRow = 0;
        segmentColumn = 1;
        setCurrList(aList);
        trackTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    trackRow = trackTable.getSelectedRow();
                    Logging.print(trackRow + ". Zeile in Tracktable ausgewählt");
                    List<Activity> xList = getCurrList();
                    createSegmentTable(xList);
                    createBarChart(xList);
                }
            }
        });
        segmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    segmentColumn = segmentTable.getSelectedColumn();
                    Logging.print(segmentColumn + ". Spalte in Segmenttable ausgewählt");
                    List<Activity> xList = getCurrList();
                    createBarChart(xList);
                }
            }
        });

        createTrackTable(aList);
        createSegmentTable(getTrackListSports(aList, sportType));
        createBarChart(getTrackListSports(aList, sportType));

        allTypes.addActionListener(ev -> {
            Logging.print("All Types selected");
            sportType = SportType.all;
            updateGUI(aList);
        });
        cycling.addActionListener(ev -> {
            Logging.print("Cycling selected");
            sportType = SportType.Cycling;
            updateGUI(aList);
        });
        driving.addActionListener(ev -> {
            Logging.print("Driving selected");
            sportType = SportType.Driving;
            updateGUI(aList);
        });
        flying.addActionListener(ev -> {
            Logging.print("Flying selected");
            sportType = SportType.Flying;
            updateGUI(aList);
        });
        hiking.addActionListener(ev -> {
            Logging.print("Hiking selected");
            sportType = SportType.Hiking;
            updateGUI(aList);
        });
        running.addActionListener(ev -> {
            Logging.print("Running selected");
            sportType = SportType.Running;
            updateGUI(aList);
        });
        skiing.addActionListener(ev -> {
            Logging.print("Skiing selected");
            sportType = SportType.Skiing;
            updateGUI(aList);
        });
        year.addActionListener(ev -> {
            Logging.print("Group by Year selected");
            groupBy = Period.YEAR;
            updateGUI(aList);
        });
        month.addActionListener(ev -> {
            Logging.print("Group by Month selected");
            groupBy = Period.MONTH;
            updateGUI(aList);
        });
        activity.addActionListener(ev -> {
            Logging.print("Group by Activity selected");
            groupBy = Period.ACTIVITY;
            updateGUI(aList);
        });

        location.addActionListener(ev -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(new JFrame());
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    Loader.updateRootDirectory(file.toPath().toString());
                    Loader.reloadData();
                } catch (ParserConfigurationException | IOException
                        | SAXException | XPathExpressionException
                        | TransformerException e) {
                    e.printStackTrace();
                }
            }
            Logging.print("root path changed");
        });
        refresh.addActionListener(ev -> {
            try {
                Loader.reloadData();
            } catch (IOException | ParserConfigurationException
                    | SAXException e) {
                e.printStackTrace();
            }
        });
        Logging.print("gui updated");
    }

    public List<Activity> getCurrList() {
        return currList;
    }

    public void setCurrList(List<Activity> aList) {
        currList = aList;
    }

    public void initiateMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        location = new JMenuItem("Change folder");
        fileMenu.add(location);
        refresh = new JMenuItem("Refresh");
        fileMenu.add(refresh);

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
        if (groupBy == Period.ACTIVITY) {
            startTime.setSelected(true);
        }
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
    }

    /**
     * creates and fills the TrackTable inside the GUI
     *
     * @param aList: List of activities
     */
    private void createTrackTable(List<Activity> aList) {
        Logging.print("aList size in TrackTable: " + aList.size());
        List<Activity> bList = getTrackListSports(aList, sportType);
        Object[][] data = new Object[bList.size()][9];

        if (groupBy != Period.ACTIVITY) {
            data = groupingByPeriod(aList, sportType);
        } else {
            int counter = 0;
            for (Activity a : bList) {
                data[counter][0] = a.getId();
                data[counter][1] = Formatter.formatDate(a.getLaps().get(0).getStartTime());
                data[counter][2] = Formatter.formatTime(a.getLaps().get(0).getStartTime());
                data[counter][3] = Formatter.formatTotalTimeSeconds(a.getActivityTotalTimeSeconds());
                data[counter][4] = Formatter.formatDistance(a.getActivityDistanceMeters());
                data[counter][5] = Formatter.formatPace(a.getActivityDistanceMeters() / a.getActivityTotalTimeSeconds());
                data[counter][6] = Formatter.formatWithoutDecimalPlaces(a.getAvgBPM());
                data[counter][7] = Formatter.formatWithoutDecimalPlaces(a.getMaxBPM());
                data[counter][8] = Formatter.formatWithoutDecimalPlaces(a.getActivityTotalAltitude());
                counter++;
            }
        }
        trackTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        trackTable.setRowSelectionAllowed(true);
        trackTable.setColumnSelectionAllowed(false);
        trackTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Date", "Start", "Time", "Distance", "Pace", "avg. BPM", "max. BPM", "Altitude"}
        ));

        setColumnVisibility(groupBy != Period.YEAR && groupBy != Period.MONTH);
        //renderCells();
        TableColumnModel columns = trackTable.getColumnModel();
        columns.getColumn(0).setMinWidth(100);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightBound = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftBound = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rightBound.setHorizontalAlignment(SwingConstants.RIGHT);
        leftBound.setHorizontalAlignment(SwingConstants.LEFT);

        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        columns.getColumn(4).setCellRenderer(rightBound);
        columns.getColumn(5).setCellRenderer(rightBound);
        columns.getColumn(6).setCellRenderer(rightBound);
        columns.getColumn(7).setCellRenderer(rightBound);
        columns.getColumn(8).setCellRenderer(rightBound);
        if (trackTable.getRowCount() > 0) trackTable.setRowSelectionInterval(0, 0);
        trackRow = 0;

    }

    private void setColumnVisibility(boolean b) {
        if (!b) {
            trackTable.getColumnModel().getColumn(1).setMinWidth(0);
            trackTable.getColumnModel().getColumn(1).setPreferredWidth(0);
            trackTable.getColumnModel().getColumn(1).setMaxWidth(0);
            trackTable.getColumnModel().getColumn(2).setMinWidth(0);
            trackTable.getColumnModel().getColumn(2).setPreferredWidth(0);
            trackTable.getColumnModel().getColumn(2).setMaxWidth(0);
        } else {
            trackTable.getColumnModel().getColumn(1).setMinWidth(TRACK_TABLE_WIDTH);
            trackTable.getColumnModel().getColumn(1).setPreferredWidth(TRACK_TABLE_WIDTH);
            trackTable.getColumnModel().getColumn(1).setMaxWidth(TRACK_TABLE_WIDTH);
            trackTable.getColumnModel().getColumn(2).setMinWidth(TRACK_TABLE_WIDTH);
            trackTable.getColumnModel().getColumn(2).setPreferredWidth(TRACK_TABLE_WIDTH);
            trackTable.getColumnModel().getColumn(2).setMaxWidth(TRACK_TABLE_WIDTH);
        }
        segmentPanel.setVisible(b);
        barChartPanel.setVisible(b);
    }

    /**
     * @param aList: List of activities
     *               creates the segmentTable on the right side of the GUI
     */
    private void createSegmentTable(List<Activity> aList) {
        if (trackTable.getRowCount() < 1) {
            segmentPanel.setVisible(false);
            return;
        } // Falls keine Daten vorhanden sind
        //Logging.print("aList size in SegmentTable: " + aList.size());
        Object[][] data = new Object[aList.get(trackRow).getLaps().size()][7];
        int counter = 0;

        double prevDistanceMeters = 0.0;
        for (Lap l : aList.get(trackRow).getLaps()) {
            data[counter][0] = counter + 1;
            data[counter][1] = Formatter.formatTotalTimeSeconds(aList.get(trackRow).getLaps().get(counter).getTotalTimeSeconds());
            data[counter][2] = Formatter.formatDistance((l.getDistanceMeters() - prevDistanceMeters));
            data[counter][3] = Formatter.formatPace(Math.round(((l.getTotalTimeSeconds() / 60) / ((l.getDistanceMeters() - prevDistanceMeters) / 1000)) * 100.0) / 100.0);
            data[counter][4] = Formatter.formatWithoutDecimalPlaces(l.getLapTotalAltitude());
            data[counter][5] = Formatter.formatWithoutDecimalPlaces(l.getAverageBPM());
            data[counter][6] = Formatter.formatWithoutDecimalPlaces(l.getMaxBPM());
            counter++;
            prevDistanceMeters = Math.round(l.getDistanceMeters());
        }
        segmentTable.setDefaultEditor(Object.class, null);  // um Werte zu fixieren
        segmentTable.setRowSelectionAllowed(false);
        segmentTable.setColumnSelectionAllowed(true);
        segmentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Segment", "Time", "Distance", "Pace", "Altitude", "avg. BPM", "max. BPM"}
        ));
        //renderCells();
        TableColumnModel columns = segmentTable.getColumnModel();
        columns.getColumn(0).setMinWidth(100);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightBound = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftBound = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        rightBound.setHorizontalAlignment(JLabel.RIGHT);
        leftBound.setHorizontalAlignment(JLabel.LEFT);

        columns = segmentTable.getColumnModel();
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(rightBound);
        columns.getColumn(3).setCellRenderer(rightBound);
        columns.getColumn(4).setCellRenderer(rightBound);
        columns.getColumn(5).setCellRenderer(rightBound);
        columns.getColumn(6).setCellRenderer(rightBound);
        segmentColumn = 1;
        segmentTable.setColumnSelectionInterval(segmentColumn, segmentColumn);
    }

    /**
     * @param aList: List of activities
     *               creates the barChart at the bottom of the GUI
     */
    private void createBarChart(List<Activity> aList) {
        if (trackTable.getRowCount() < 1) {
            barChartPanel.setVisible(false);
            return;
        } // Falls keine Daten vorhanden sind
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
            case 1 -> {
                dataset = timeDataset;
                yAxis = "Time  [min]";
                nameLabel.setText("Time");
            }
            case 2 -> {
                dataset = distanceDataset;
                yAxis = "Distance  [m]";
                nameLabel.setText("Distance");
            }
            case 3 -> {
                dataset = paceDataset;
                yAxis = "Pace  [min/km]";
                nameLabel.setText("Pace");
            }
            case 4 -> {
                dataset = altitudeDataset;
                yAxis = "Altitude  [m]";
                nameLabel.setText("Altitude");
            }
            case 5 -> {
                dataset = avgBpmDataset;
                yAxis = "Average BPM  [beats/min]";
                nameLabel.setText("Average BPM");
            }
            case 6 -> {
                dataset = maxBpmDataset;
                yAxis = "Max. BPM  [beats/min]";
                nameLabel.setText("Max. BPM");
            }
            default -> Logging.print("Default case detected");
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

    /**
     * @param aList:     List of activities
     * @param sportType: ENUM SportType value
     * @return List of first selected sportTypes in the ENUM SportType (f.e. Running)
     */
    public List<Activity> getTrackListSports(List<Activity> aList, SportType sportType) {
        if (sportType == SportType.all) return aList;
        return aList.stream().filter(a -> a.getSport().equals(String.valueOf(sportType)))
                .toList();
    }

    /**
     * @param aList:     List of activities
     * @param sportType: ENUM SportType value
     * @return returns a List<List<Activity>> with grouped items which is selected in the GUI, sorted by date
     */
    public List<List<Activity>> getListOfListSorted(List<Activity> aList, SportType sportType) {
        List<Activity> filteredBySportList = getTrackListSports(aList, sportType);
        List<List<Activity>> temp = new ArrayList<>();
        if (groupBy == Period.YEAR) {
            List<List<Activity>> list = new ArrayList<>();
            for (List<Activity> activities : filteredBySportList.stream()
                    .collect(groupingBy(a -> a.getDate().getYear()))
                    .values()) {
                list.add(activities);
            }
            list.sort(Comparator.comparing(o -> o.get(0).getDate()));
            temp = list;
        } else if (groupBy == Period.MONTH) {
            List<List<Activity>> list = new ArrayList<>();
            for (List<Activity> activities : filteredBySportList.stream()
                    .collect(groupingBy(data1 -> data1.getDate().getMonth() + "-" + data1.getDate().getYear()))
                    .values()) {
                list.add(activities);
            }
            list.sort(Comparator.comparing(o -> o.get(0).getDate()));
            temp = list;
        }
        return temp;
    }

    /**
     * @param aList:     List of activities
     * @param sportType: ENUM SportType value
     * @return an object[][] which contains the data to fill into the GUI-Tables in method createTrackTable(List<Activity> aList)
     */
    public Object[][] groupingByPeriod(List<Activity> aList, SportType sportType) {
        List<List<Activity>> temp = getListOfListSorted(aList, sportType);
        Object[][] data = new Object[temp.size()][9];
        int counter = 0;

        for (List<Activity> list : temp) {
            double totalDistance = 0;
            double totalTime = 0;
            double totalPace = 0;
            int avgBPM = 0;
            int maxBPM = Integer.MIN_VALUE;
            double totalAltitude = 0;

            for (Activity a : list) {
                totalDistance += a.getActivityDistanceMeters();
                totalTime += a.getActivityTotalTimeSeconds();
                totalPace += totalDistance / totalTime;
                avgBPM += a.getAvgBPM();
                if (a.getMaxBPM() > maxBPM) maxBPM = (int) a.getMaxBPM();
                totalAltitude += a.getActivityTotalAltitude();
            }
            avgBPM /= list.size();
            if (groupBy == Period.YEAR) data[counter][0] = list.get(0).getLaps().get(0).getStartTime().getYear();
            else
                data[counter][0] = list.get(0).getLaps().get(0).getStartTime().getMonth() + " [" + list.get(0).getLaps().get(0).getStartTime().getYear() + "]";
            data[counter][1] = -1;  //date
            data[counter][2] = -1;  //start
            data[counter][3] = Formatter.formatTotalTimeSeconds(totalTime);
            data[counter][4] = Formatter.formatDistance(totalDistance);
            data[counter][5] = Formatter.formatPace(totalPace);
            data[counter][6] = avgBPM;
            data[counter][7] = maxBPM;
            data[counter][8] = Formatter.formatDistance(totalAltitude);
            counter++;
        }
        return data;
    }

    public static class BarChart extends ApplicationFrame {

        @Serial
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
            /*
                chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
                     + "format (lower bars = better performance)"));
            */
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

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!startTime.isSelected()) {
                trackTable.getColumnModel().getColumn(2).setMinWidth(0);
                trackTable.getColumnModel().getColumn(2).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(2).setMaxWidth(0);
                Logging.print("JMenuItemAction: startTime deselected - Width:" + trackTable.getColumnModel().getColumn(2).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(2).setMinWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(2).setMaxWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(2).setPreferredWidth(TRACK_TABLE_WIDTH);
                Logging.print("JMenuItemAction: startTime selected - Width:" + trackTable.getColumnModel().getColumn(2).getWidth());
            }

            int segmentTableWidth = 100;
            if (!pace.isSelected()) {
                trackTable.getColumnModel().getColumn(5).setMinWidth(0);
                trackTable.getColumnModel().getColumn(5).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(5).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(3).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(3).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(3).setMaxWidth(0);
                Logging.print("JMenuItemAction: pace deselected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(5).setMinWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(5).setMaxWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(5).setPreferredWidth(TRACK_TABLE_WIDTH);
                segmentTable.getColumnModel().getColumn(3).setMinWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(3).setMaxWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(3).setPreferredWidth(segmentTableWidth);
                Logging.print("JMenuItemAction: pace selected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            }

            if (!averageBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(6).setMinWidth(0);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(5).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(5).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(5).setMaxWidth(0);
                Logging.print("JMenuItemAction: averageBpm deselected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(6).setMinWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(TRACK_TABLE_WIDTH);
                segmentTable.getColumnModel().getColumn(5).setMinWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(5).setMaxWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(5).setPreferredWidth(segmentTableWidth);
                Logging.print("JMenuItemAction: averageBpm selected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            }

            if (!maxBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(7).setMinWidth(0);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(6).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(6).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(6).setMaxWidth(0);
                Logging.print("JMenuItemAction: maxBpm deselected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(7).setMinWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(TRACK_TABLE_WIDTH);
                segmentTable.getColumnModel().getColumn(6).setMinWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(6).setMaxWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(6).setPreferredWidth(segmentTableWidth);
                Logging.print("JMenuItemAction: maxBpm selected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            }

            if (!heightLvl.isSelected()) {
                trackTable.getColumnModel().getColumn(8).setMinWidth(0);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(4).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(4).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(4).setMaxWidth(0);
                Logging.print("JMenuItemAction: heightLvl deselected - Width:" + trackTable.getColumnModel().getColumn(8).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(8).setMinWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(TRACK_TABLE_WIDTH);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(TRACK_TABLE_WIDTH);
                segmentTable.getColumnModel().getColumn(4).setMinWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(4).setMaxWidth(segmentTableWidth);
                segmentTable.getColumnModel().getColumn(4).setPreferredWidth(segmentTableWidth);
                Logging.print("JMenuItemAction: heightLvl selected - Width:" + trackTable.getColumnModel().getColumn(8).getWidth());
            }
        }
    }
}