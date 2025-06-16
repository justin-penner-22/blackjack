package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; // Expliziter Import für BufferedImage
import javax.imageio.ImageIO;
import java.io.IOException; // Expliziter Import für IOException

public class BackgroundPanel extends JPanel {

    // Verwende einen aussagekräftigen Namen für die Instanzvariable, die das Hintergrundbild hält.
    private BufferedImage loadedBackgroundImage;

    public BackgroundPanel() {
        // Gute Praxis: Hintergrund des Panels transparent machen,
        // da das Bild den gesamten Bereich überdecken soll.
        // Das ist nicht unbedingt notwendig, aber kann in manchen Szenarien helfen,
        // unnötiges Zeichnen zu vermeiden oder Transparenz zu handhaben.
        setOpaque(false);

        // Laden des Hintergrundbildes im Konstruktor.
        // Die Fehlerbehandlung ist wichtig, falls das Bild nicht gefunden wird.
        try {
            // getClass().getResource() sucht im Klassenpfad.
            // Der führende Schrägstrich '/' bedeutet, dass er vom Root des Klassenpfads ausgeht.
            // Stelle sicher, dass "background.jpg" wirklich im Ordner "images" im Root deines JARs liegt.
            loadedBackgroundImage = ImageIO.read(getClass().getResource("/images/background.jpg"));

            // Zusätzliche Überprüfung, ob das Bild geladen wurde, auch wenn keine IOException geworfen wurde.
            // Manchmal gibt getResource() null zurück, was dann eine IllegalArgumentException in ImageIO.read()
            // oder eine NullPointerException beim Zugriff auf loadedBackgroundImage verursachen könnte,
            // wenn es nicht behandelt wird.
            if (loadedBackgroundImage == null) {
                System.err.println("Fehler: Hintergrundbild '/images/background.jpg' konnte nicht gefunden werden.");
            }
        } catch (IOException e) {
            // Fange die spezifische IOException ab, die beim Lesen des Bildes auftreten kann.
            System.err.println("Fehler beim Laden des Hintergrundbildes aus Datei: " + e.getMessage());
            e.printStackTrace(); // Ausgabe des Stack-Traces zur detaillierten Fehleranalyse
        } catch (IllegalArgumentException e) {
            // Fange IllegalArgumentException ab, falls getResource() null zurückgibt und ImageIO.read() damit nicht umgehen kann.
            System.err.println("Fehler: Der Pfad zum Hintergrundbild '/images/background.jpg' ist ungültig oder das Bild existiert nicht. " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Zuerst die Standard-Zeichenroutine der Superklasse aufrufen.
        // Das löscht normalerweise den Hintergrund des Panels, was wichtig ist,
        // bevor wir unser Bild zeichnen.
        super.paintComponent(g);

        // Prüfen, ob das Bild erfolgreich geladen wurde.
        if (loadedBackgroundImage != null) {
            // Wir casten Graphics zu Graphics2D, um erweiterte Zeichenoptionen nutzen zu können,
            // insbesondere die Rendering Hints für eine bessere Skalierungsqualität.
            Graphics2D g2d = (Graphics2D) g;

            // --- Rendering Hints für bestmögliche Qualität beim Skalieren ---
            // Diese Hints weisen Java an, wie es beim Zeichnen und Skalieren vorgehen soll.
            // Sie können die visuelle Qualität stark verbessern, besonders wenn Bilder vergrößert werden.

            // KEY_INTERPOLATION: Steuert den Algorithmus zur Interpolation von Pixeln beim Skalieren.
            // - VALUE_INTERPOLATION_BILINEAR: Guter Kompromiss zwischen Qualität und Leistung.
            // - VALUE_INTERPOLATION_BICUBIC: Höchste Qualität, aber rechenintensiver. Für ein Hintergrundbild oft die beste Wahl.
            // - VALUE_INTERPOLATION_NEAREST_NEIGHBOR: Schnellste, aber schlechteste Qualität (pixelig bei Vergrößerung).
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            // KEY_RENDERING: Gibt an, ob der Renderer Qualität oder Geschwindigkeit bevorzugt.
            // - VALUE_RENDER_QUALITY: Priorisiert die visuelle Qualität.
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // KEY_ANTIALIASING: Glättet die Kanten von gezeichneten Formen und Text.
            // Bei Bildern hilft es, gezackte Kanten zu reduzieren, die durch Skalierung entstehen können.
            // VALUE_ANTIALIAS_ON: Aktiviert das Antialiasing.
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // KEY_ALPHA_INTERPOLATION: Verbessert die Qualität der Transparenz (Alpha-Werte) beim Skalieren.
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            // --- Das Bild einmal über das gesamte Panel skalieren und zeichnen ---
            // g.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
            // - loadedBackgroundImage: Das zu zeichnende Bild.
            // - 0, 0: Die X- und Y-Koordinaten, an denen das Bild gezeichnet werden soll (obere linke Ecke).
            // - getWidth(), getHeight(): Die aktuelle Breite und Höhe dieses Panels.
            //   Das Bild wird auf diese Dimensionen skaliert.
            // - this: Der ImageObserver, in diesem Fall das Panel selbst.
            g2d.drawImage(loadedBackgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Optional, aber empfohlen für Layout Manager:
    // Gibt die bevorzugte Größe des Panels an.
    // Wenn das Bild den gesamten verfügbaren Platz ausfüllen soll,
    // ist diese Methode weniger kritisch, da Layout Manager oft die Größe strecken.
    // Aber für konsistentes Verhalten kann es hilfreich sein, die Größe des Originalbildes
    // als Referenz zu übergeben, falls der Container das Panel nicht stark streckt.
    @Override
    public Dimension getPreferredSize() {
        if (loadedBackgroundImage != null) {
            return new Dimension(loadedBackgroundImage.getWidth(), loadedBackgroundImage.getHeight());
        }
        // Fallback-Größe, falls das Bild nicht geladen werden konnte.
        return new Dimension(800, 600); // Oder eine andere sinnvolle Standardgröße
    }
}