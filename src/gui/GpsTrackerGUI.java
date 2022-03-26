package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.chart.title.DateTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import static gui.BarChartDemo1.createChart;
import static gui.BarChartDemo1.createDataset;
import static org.jfree.chart.ChartFactory.createBarChart;

public class GpsTrackerGUI extends JFrame {

    private JPanel rootPanel;
    private JTable trackTable;
    private JTable segmentTable;
    private JPanel Tracks;
    private JPanel chartPanel;

    public GpsTrackerGUI(String title) {
        super(title);
        initiateMenuBar(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(rootPanel);
        this.pack();
        createTrackTable();
        createSegmentTable();
        createBarChart();
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
        JCheckBoxMenuItem startTime = new JCheckBoxMenuItem("Start");
        startTime.setSelected(true);
        columnMenu.add(startTime);
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

    private void createTrackTable(){
        Object [][] data = {
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "5", "111", "160", "60"},
        };
        trackTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Date", "Start", "Distance", "Time", "Pace", "avg. BPM", "max. BPM", "height"} // String ändern wenn Spalten ausgeblendet werden sollen
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


    private void createSegmentTable(){
        Object [][] data = {
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
                {"1", "05:00", "8", "111", "180"},
        };
        segmentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"km", "Time", "height", "avg. BPM", "max. BPM"}
        ));
        TableColumnModel columns = segmentTable.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        columns.getColumn(4).setCellRenderer(centerRenderer);
    }


    private void createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(20, "", "Tag 1");
        dataset.setValue(15, "", "Tag 2");
        dataset.setValue(10, "", "Tag 3");
        dataset.setValue(17, "", "Tag 4");
        dataset.setValue(25, "", "Tag 5");
        dataset.setValue(18, "", "Tag 6");
        dataset.setValue(35, "", "Tag 7");


        JFreeChart chart = createChart(dataset);

        ChartPanel chPanel = new ChartPanel(chart); //creating the chart panel, which extends JPanel
        chPanel.setPreferredSize(new Dimension(800, 250)); //size according to my window
        chPanel.setMouseWheelEnabled(true);
        chPanel.setFillZoomRectangle(true);


        chartPanel.add(chPanel); //add the chart viewer to the JPanel

        //chartPanel.add(chPanel); //add the chart viewer to the JPanel

    }
}
