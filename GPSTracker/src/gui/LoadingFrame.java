package gui;

import javax.swing.*;

public class LoadingFrame extends JFrame {
    private JPanel rootPanel;
    private JLabel loadingText;
    private JLabel iconLabel;

    public LoadingFrame(String title){
        super(title);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPane(rootPanel);
        this.pack();

        Icon icon = new ImageIcon("animations/RunningMan_small.gif");
        JLabel gif = new JLabel(icon);
        iconLabel.add(gif);
        this.getContentPane().add(gif);
        this.pack();
        this.setVisible(true);
    }
}
