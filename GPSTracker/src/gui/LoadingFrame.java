package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadingFrame extends JFrame {
    private JPanel rootPanel;
    private JLabel loadingText;
    private JLabel iconPanel;

    public LoadingFrame(String title){
        super(title);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPane(rootPanel);
        this.pack();
        File file = new File("RunningMan.gif");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon ii = new ImageIcon(image);
        iconPanel.setIcon(ii);
    }
}
