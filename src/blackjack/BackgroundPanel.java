package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/background.jpg"));
        } catch (IOException e) {
            System.err.println("Hintergrundbild konnte nicht geladen werden.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Muster kacheln (repeaten)
            for (int x = 0; x < getWidth(); x += backgroundImage.getWidth()) {
                for (int y = 0; y < getHeight(); y += backgroundImage.getHeight()) {
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        }
    }
}
