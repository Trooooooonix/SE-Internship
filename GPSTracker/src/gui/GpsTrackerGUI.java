package gui;

import handlers.Loader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.xml.sax.SAXException;
import tracks.Activity;
import tracks.Lap;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static gui.GpsTrackerGUI.BarChart.createChart;

public class GpsTrackerGUI extends JFrame {
    public String yAxis;
    private JPanel rootPanel;
    private JTable trackTable;
    private JTable segmentTable;
    private JPanel chartPanel;
    private JPanel Tracks;      //needed
    private JLabel nameLabel;
    private JPanel SegmentPanel;
    private JPanel barChartPanel;
    public int trackRow = 0;
    public int segmentColumn = 1;
    public DefaultCategoryDataset dataset;
    public JCheckBoxMenuItem startTime, pace, averageBpm, maxBpm, heightLvl;
    public JMenuItem location, year, month, activity;
    public JMenuItem running, cycling, driving, allTypes, flying, hiking, skiing;
    public int tableWidth = 75;
    public SportType sportType = SportType.all;
    public Period groupBy = Period.ACTIVITY;


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
                segmentTable.getColumnModel().getColumn(3).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(3).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(3).setMaxWidth(0);
                System.out.println("JMenuItemAction: pace deselected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(5).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(5).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(5).setPreferredWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(3).setMinWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(3).setMaxWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(3).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: pace selected - Width:" + trackTable.getColumnModel().getColumn(5).getWidth());
            }

            if (!averageBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(6).setMinWidth(0);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(5).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(5).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(5).setMaxWidth(0);
                System.out.println("JMenuItemAction: averageBpm deselected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(6).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(6).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(6).setPreferredWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(5).setMinWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(5).setMaxWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(5).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: averageBpm selected - Width:" + trackTable.getColumnModel().getColumn(6).getWidth());
            }

            if (!maxBpm.isSelected()) {
                trackTable.getColumnModel().getColumn(7).setMinWidth(0);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(6).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(6).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(6).setMaxWidth(0);
                System.out.println("JMenuItemAction: maxBpm deselected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(7).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(7).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(7).setPreferredWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(6).setMinWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(6).setMaxWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(6).setPreferredWidth(tableWidth);
                System.out.println("JMenuItemAction: maxBpm selected - Width:" + trackTable.getColumnModel().getColumn(7).getWidth());
            }

            if (!heightLvl.isSelected()) {
                trackTable.getColumnModel().getColumn(8).setMinWidth(0);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(0);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(0);
                segmentTable.getColumnModel().getColumn(4).setMinWidth(0);
                segmentTable.getColumnModel().getColumn(4).setPreferredWidth(0);
                segmentTable.getColumnModel().getColumn(4).setMaxWidth(0);
                System.out.println("JMenuItemAction: heightLvl deselected - Width:" + trackTable.getColumnModel().getColumn(8).getWidth());
            } else {
                trackTable.getColumnModel().getColumn(8).setMinWidth(tableWidth);
                trackTable.getColumnModel().getColumn(8).setMaxWidth(tableWidth);
                trackTable.getColumnModel().getColumn(8).setPreferredWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(4).setMinWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(4).setMaxWidth(tableWidth);
                segmentTable.getColumnModel().getColumn(4).setPreferredWidth(tableWidth);
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
        System.out.println(segmentTable.getColumnModel().getColumn(4).getWidth());
        System.out.println(trackTable.getColumnModel().getColumn(4).getWidth());

        allTypes.addActionListener(ev -> {
            //System.out.println("All Types selected");
            sportType = SportType.all;
            updateGUI(aList);
        });

        cycling.addActionListener(ev -> {
            //System.out.println("Cycling selected");
            sportType = SportType.Cycling;
            updateGUI(aList);
        });

        driving.addActionListener(ev -> {
            //System.out.println("Driving selected");
            sportType = SportType.Driving;
            updateGUI(aList);
        });

        flying.addActionListener(ev -> {
            //System.out.println("Flying selected");
            sportType = SportType.Flying;
            updateGUI(aList);
        });

        hiking.addActionListener(ev -> {
            //System.out.println("Hiking selected");
            sportType = SportType.Hiking;
            updateGUI(aList);
        });

        running.addActionListener(ev -> {
            //System.out.println("Running selected");
            sportType = SportType.Running;
            updateGUI(aList);
        });

        skiing.addActionListener(ev -> {
            //System.out.println("Skiing selected");
            sportType = SportType.Skiing;
            updateGUI(aList);
        });

        year.addActionListener(ev -> {
            //System.out.println("Group by Year selected");
            groupBy = Period.YEAR;
            updateGUI(aList);
        });

        month.addActionListener(ev -> {
            //System.out.println("Group by Month selected");
            groupBy = Period.MONTH;
            updateGUI(aList);
        });

        activity.addActionListener(ev -> {
            //System.out.println("Group by Activity selected");
            groupBy = Period.ACTIVITY;
            updateGUI(aList);
        });

        location.addActionListener(ev -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(new JFrame());
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try {
                    Loader.updateRootDirectory(file.toPath().toString());
                    this.dispose();
                    Loader.initLoading();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateGUI(List<Activity> aList) {
        createTrackTable(aList);
        // Gruppieren nach Zeitraum
        trackRow = 0;
        createSegmentTable(getTrackListSports(aList, sportType));
        segmentColumn = 1;
        createBarChart(getTrackListSports(aList, sportType));
    }

    public void initiateMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        location = new JMenuItem("Change folder");
        fileMenu.add(location);

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
        for (Activity a : aList) {
            a.setDate();
        }
        final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Activity> bList = getTrackListSports(aList, sportType);
        Object[][] data = new Object[bList.size()][9];

        if (groupBy != Period.ACTIVITY) {
            data = groupingByPeriod(aList, sportType);
        } else {
            int counter = 0;
            double activityDistanceMeters;
            double activityTotalTimeSeconds;

            for (Activity a : bList) {
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

        if(groupBy == Period.YEAR || groupBy == Period.MONTH){
            trackTable.getColumnModel().getColumn(1).setMinWidth(0);
            trackTable.getColumnModel().getColumn(1).setPreferredWidth(0);
            trackTable.getColumnModel().getColumn(1).setMaxWidth(0);
            trackTable.getColumnModel().getColumn(2).setMinWidth(0);
            trackTable.getColumnModel().getColumn(2).setPreferredWidth(0);
            trackTable.getColumnModel().getColumn(2).setMaxWidth(0);
            SegmentPanel.setVisible(false);
            barChartPanel.setVisible(false);
        } else{
            trackTable.getColumnModel().getColumn(1).setMinWidth(tableWidth);
            trackTable.getColumnModel().getColumn(1).setPreferredWidth(tableWidth);
            trackTable.getColumnModel().getColumn(1).setMaxWidth(tableWidth);
            trackTable.getColumnModel().getColumn(2).setMinWidth(tableWidth);
            trackTable.getColumnModel().getColumn(2).setPreferredWidth(tableWidth);
            trackTable.getColumnModel().getColumn(2).setMaxWidth(tableWidth);
            SegmentPanel.setVisible(true);
            barChartPanel.setVisible(true);
        }

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
            case 1 -> {
                dataset = distanceDataset;
                yAxis = "Distance  [m]";
                nameLabel.setText("Distance");
            }
            case 2 -> {
                dataset = timeDataset;
                yAxis = "Time  [min]";
                nameLabel.setText("Time");
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

    public List<Activity> getTrackListSports(List<Activity> aList, SportType sportType) {
        if (sportType == SportType.all) return aList;
        return aList.stream().filter(a -> a.getSport().equals(String.valueOf(sportType)))
                .collect(Collectors.toList());
    }

    public List<List<Activity>> getListOfList(List<Activity> aList, SportType sportType) {
        List<Activity> filteredBySportList = getTrackListSports(aList, sportType);
        List<List<Activity>> temp = new ArrayList<>();
        if (groupBy == Period.YEAR) {
            temp = filteredBySportList.stream()
                    .collect(Collectors.groupingBy(a -> a.getDate().getYear()))
                    .values().stream().toList();
        } else if (groupBy == Period.MONTH) {
            temp = filteredBySportList.stream()
                    .collect(Collectors.groupingBy(a -> a.getDate().getMonth()))
                    .values().stream().toList();
        }
        return temp;
    }

    public Object[][] groupingByPeriod(List<Activity> aList, SportType sportType) {
        List<List<Activity>> temp = getListOfList(aList, sportType);

        Object[][] data = new Object[temp.size()][9];
        int counter = 0;
        double totalDistance = 0;
        double totalTime = 0;
        double totalPace = 0;
        int avgBPM = 0;
        int maxBPM = Integer.MIN_VALUE;
        double totalAltitude = 0;

        for (List<Activity> list : temp) {
            for (Activity a : list) {
                totalDistance += a.getActivityDistanceMeters();
                totalTime += a.getActivityTotalTimeSeconds();
                totalPace += totalDistance / totalTime;
                avgBPM += a.getAvgBPM();
                if (a.getMaxBPM() > maxBPM) maxBPM = (int) a.getMaxBPM();
                totalAltitude += a.getActivityTotalAltitude();
            }
            avgBPM /= list.size();
            if(groupBy == Period.YEAR) data[counter][0] = list.get(0).getLaps().get(0).getStartTime().getYear();
            else data[counter][0] = list.get(0).getLaps().get(0).getStartTime().getMonth();
            data[counter][1] = -1;  //date
            data[counter][2] = -1;  //start
            data[counter][3] = distanceFormatter.format(totalDistance);
            data[counter][4] = String.format("%02d:%02d:%02d", (int) totalTime / 3600, ((int) totalTime % 3600) / 60, ((int) totalTime % 60));
            data[counter][5] = paceFormatter.format(totalPace);
            data[counter][6] = avgBPM;
            data[counter][7] = maxBPM;
            if (totalAltitude >= 1000) data[counter][8] = distanceFormatter.format(totalAltitude);
            else data[counter][8] = totalAltitude;
            counter++;
        }
        return data;
    }

}