package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        /*
            creating the window for the GUI
            setting a windowsize and a default operation to close upon clicking the "X" in the top-right
         */
        JFrame window = new JFrame("GPS-Tracker GUI");
        window.setSize(1920,1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame optionWindow = new JFrame("Options");
        optionWindow.setSize(1220,980);
        optionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        /*
            adding ActionListeners to react on click
         */
        option.addActionListener(a -> {
            window.setVisible(false);
            optionWindow.setVisible(true);
        });

        JMenu fileMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        JMenuItem refresh = new JMenuItem("Refresh");
        fileMenu.add(refresh);
        /*
            adding ActionListeners to react on click
         */
        refresh.addActionListener(a -> window.repaint());

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);


        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);


        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        doneButton.setSize(200, 200);
        optionWindow.add(doneButton, BorderLayout.SOUTH);
        doneButton.addActionListener(a -> {
            optionWindow.setVisible(false);
            window.setVisible(true);
        });



        window.setVisible(true);
    }
}
