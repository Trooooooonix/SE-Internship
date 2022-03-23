package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class GpsTrackerGUI extends JFrame{

    private JPanel rootPanel;
    private JTable trackTable;

    public GpsTrackerGUI(String title){
        super(title);
        this.setContentPane(rootPanel);
        this.pack();
        createTable();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createTable(){
        Object [][] data = {
                {"test1", "11.11.1111", "11:11", "11", "11:11", "111"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "111"},
                {"test1", "11.11.1111", "11:11", "11", "11:11", "111"}
        };
        trackTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Datum", "Startzeit", "Strecke", "Zeit", "BPM"}
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
    }



}
