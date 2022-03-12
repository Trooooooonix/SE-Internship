package gui;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        /*
            creating the window for the GUI
         */
        JFrame window = new JFrame("GPS-Tracker GUI");
        window.setSize(1920,1080);
        /*
            adding the menubar to the window
         */
        JMenuBar menuBar = new JMenuBar();
        window.add(menuBar);
        /*
            adding items to the Menubar
         */
        JMenuItem item1 = new JMenuItem("Option");
        menuBar.add(item1);
        JMenuItem item2 = new JMenuItem("File");
        menuBar.add(item2);
        JMenuItem item3 = new JMenuItem("Navigate");
        menuBar.add(item3);
        JMenuItem item4 = new JMenuItem("Refactor");
        menuBar.add(item4);
        JMenuItem item5 = new JMenuItem("Update View");
        menuBar.add(item5);



        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }
}
