package gui;

import javax.swing.*;
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
            adding the menubar to the window
         */
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        /*
            adding Menus to the Menubar
         */
        JMenu navigationMenu = new JMenu("Navigate");
        menuBar.add(navigationMenu);
        JMenuItem option = new JMenuItem("Option");
        navigationMenu.add(option);
        option.addActionListener(e -> {
            window.setVisible(false);
            optionWindow.setVisible(true);
        });

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem refactor = new JMenuItem("Refactor");
        fileMenu.add(refactor);
        JMenuItem refresh = new JMenuItem("Refresh");
        fileMenu.add(refresh);


        window.setVisible(true);

    }
}
