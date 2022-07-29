package gui;

import javax.swing.*;
import java.util.Objects;


/**
 * This Class is responsible for creating the LoadingFrame
 */
public class LoadingFrame extends JFrame {
    private JPanel rootPanel;
    private JLabel loadingText; //needed for form
    private JLabel iconLabel;

    /**
     * Creates the Loading Frame
     *
     * @param title
     */
    public LoadingFrame(String title) {
        super(title);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPane(rootPanel);
        this.pack();

        Icon icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("animations/RunningMan_small.gif")));
        JLabel gif = new JLabel(icon);
        iconLabel.add(gif);
        this.getContentPane().add(gif);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initiates the LoadingFrame
     */
    public void initFrame() {
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Disposes the Loading Frame
     */
    public void deleteFrame() {
        this.dispose();
    }

}
