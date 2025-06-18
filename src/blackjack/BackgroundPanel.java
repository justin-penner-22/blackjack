package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.IOException; 

public class BackgroundPanel extends JPanel {

    private BufferedImage loadedBackgroundImage;

    public BackgroundPanel() {
        setOpaque(false);

        try {
            loadedBackgroundImage = ImageIO.read(getClass().getResource("/images/background.jpg"));

            if (loadedBackgroundImage == null) {
                System.err.println("Fehler: Hintergrundbild '/images/background.jpg' konnte nicht gefunden werden.");
            }
        } catch (IOException e) { // Fals der Pfad oder Datei nicht gefunden
            System.err.println("Fehler beim Laden des Hintergrundbildes aus Datei: " + e.getMessage());
            e.printStackTrace(); 
        } catch (IllegalArgumentException e) { // Falscher Pfad oder ungültige Ressource
            System.err.println("Fehler: Der Pfad zum Hintergrundbild '/images/background.jpg' ist ungültig oder das Bild existiert nicht. " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (loadedBackgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            
            // sorgt für eine bessere Bildqualität
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.drawImage(loadedBackgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (loadedBackgroundImage != null) {
            return new Dimension(loadedBackgroundImage.getWidth(), loadedBackgroundImage.getHeight());
        }
        return new Dimension(800, 600); 
    }
}