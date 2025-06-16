package blackjack;

/** Java Bibiotheken **************************************************************************************************************************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.imageio.ImageIO; 
import java.net.URL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI extends JFrame {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private JTextArea playerArea;
    private JTextArea dealerArea;
    private JLabel statusLabel;
    private JLabel balanceLabel;
    private JTextField betField;
    private JButton betButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JButton exitButton; 

    private int initialBalance; 

    /** GUI initialisieren ****************************************************************************************************************************************************/
    public BlackjackGUI() {
        super("Blackjack Spiel");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo((Component) null);

        BackgroundPanel background = new BackgroundPanel();
        background.setLayout(new BorderLayout());

        initializeGame();

        JPanel infoPanel = createInfoPanel();
        JPanel handsPanel = createHandsPanel();
        JPanel controlPanel = createControlPanel();

    /** Top Panel ************************************************************************************************************************************************************/
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setOpaque(false);
    
    // Titelbild hinzufügen
    try {
        // Pfad relativ zum Klassenpfad (innerhalb des JARs oder des bin-Ordners)
        URL imageUrl = getClass().getResource("/images/blackjack-title.png");
    
        if (imageUrl != null) {
            ImageIcon titleIcon = new ImageIcon(ImageIO.read(imageUrl));
            JLabel titleLabel = new JLabel(titleIcon);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(titleLabel, BorderLayout.WEST);
        } else {
            System.err.println("Ressourcenbild wurde nicht gefunden: /images/blackjack-title.png");
            // Fallback
            JLabel fallbackTitle = new JLabel("Blackjack Spiel");
            fallbackTitle.setFont(new Font("Verdana", Font.BOLD, 24));
            fallbackTitle.setForeground(Color.WHITE);
            fallbackTitle.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(fallbackTitle, BorderLayout.WEST);
        }
    } catch (Exception e) {
        System.err.println("Fehler beim Laden des Ressourcenbildes: " + e.getMessage());
        // Fallback
        JLabel fallbackTitle = new JLabel("Blackjack Spiel");
        fallbackTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        fallbackTitle.setForeground(Color.WHITE);
        fallbackTitle.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(fallbackTitle, BorderLayout.WEST);
    }


    topPanel.add(infoPanel, BorderLayout.CENTER);
    topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    background.add(topPanel, BorderLayout.NORTH);
    background.add(handsPanel, BorderLayout.CENTER);
    background.add(controlPanel, BorderLayout.SOUTH);

    setContentPane(background);
    }

    private void initializeGame() {
        deck = new Deck();
        player = new Player(1000);
        dealer = new Dealer();
        initialBalance = player.getBalance(); // Anfangsguthaben setzen
    }

    /** Info Panel  ***********************************************************************************************************************************************************/
    private JPanel createInfoPanel() { 
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10)); // GridLayout mit vertikalem Abstand
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0)); //Außenabstand oben/unten

        balanceLabel = new JLabel("Guthaben: " + player.getBalance() + "€");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(balanceLabel, BorderLayout.EAST);

        statusLabel = new JLabel("Bitte geben Sie Ihren Einsatz und drücken Sie 'Setzen'");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(statusLabel, BorderLayout.EAST);

        panel.add(balanceLabel);
        panel.add(statusLabel);
        return panel;
    }

    /** Player Hand und Dealer Hand *******************************************************************************************************************************************/
    private JPanel createHandsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerArea = new JTextArea();
        playerArea.setFont(new Font("Verdana", Font.PLAIN, 18)); 
        playerArea.setEditable(false);
        playerArea.setOpaque(false);
        playerArea.setForeground(Color.WHITE);
        playerArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), "Spieler Hand",
            0, 0, new Font("Verdana", Font.BOLD, 16), Color.WHITE));

        dealerArea = new JTextArea();
        dealerArea.setFont(new Font("Verdana", Font.PLAIN, 18));
        dealerArea.setEditable(false);
        dealerArea.setOpaque(false);
        dealerArea.setForeground(Color.WHITE);
        dealerArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), "Dealer Hand",
            0, 0, new Font("Verdana", Font.BOLD, 16), Color.WHITE));

        JScrollPane playerScroll = new JScrollPane(playerArea);
        playerScroll.setOpaque(false);
        playerScroll.getViewport().setOpaque(false);
        playerScroll.setBorder(null);

        JScrollPane dealerScroll = new JScrollPane(dealerArea);
        dealerScroll.setOpaque(false);
        dealerScroll.getViewport().setOpaque(false);
        dealerScroll.setBorder(null);

        panel.add(playerScroll);
        panel.add(dealerScroll);

        return panel;
    }
    
    /** Bottom Panel ************************************************************************************************************************************************************/
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 80, 0));

        JLabel betLabel = new JLabel("Einsatz: ");
        betLabel.setForeground(Color.WHITE);
        betLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        betField = new JTextField(10);
        betField.setFont(new Font("Verdana", Font.PLAIN, 16));

        betButton = new JButton("Setzen");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        exitButton = new JButton("Exit");

        JButton[] buttons = {betButton, hitButton, standButton, newGameButton, exitButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Verdana", Font.BOLD, 16));
            btn.setPreferredSize(new Dimension(140, 40));
            btn.setBackground(new Color(0, 80, 0));
            btn.setForeground(Color.WHITE);
        }

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(false);
        exitButton.setEnabled(true);

        panel.add(new JLabel(""));
        panel.add(betLabel);
        panel.add(betField);
        panel.add(betButton);
        panel.add(hitButton);
        panel.add(standButton);
        panel.add(newGameButton);
        panel.add(exitButton);

        betButton.addActionListener(e -> placeBet());
        hitButton.addActionListener(e -> playerHit());
        standButton.addActionListener(e -> playerStand());
        newGameButton.addActionListener(e -> resetRound());
        exitButton.addActionListener(e -> showExitDialog());

        return panel;
    }

    /** Beenden Window ***********************************************************************************************************************************************************/
    private void placeBet() {
        try {
            int bet = Integer.parseInt(betField.getText());
            player.placeBet(bet);
            balanceLabel.setText("Guthaben: " + player.getBalance() + "€");
            statusLabel.setText("Einsatz gesetzt: " + bet + "€. Drücken Sie 'Hit' oder 'Stand'.");
            startRound();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte eine gültige Zahl eingeben.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /** Start Round ***************************************************************************************************************************************************************/
    private void startRound() {
        player.clearHand();
        dealer.clearHand();
        playerArea.setText("");
        dealerArea.setText("");

        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        updateHands(false);
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        betButton.setEnabled(false);
        betField.setEnabled(false);
        exitButton.setEnabled(true);
    }

    /** Update Hands ***************************************************************************************************************************************************************/
    private void updateHands(boolean revealDealer) {
        playerArea.setText("");
        for (Card card : player.getHand().getCards()) {
            playerArea.append(card.toString() + "\n");
        }
        playerArea.append("Wert: " + player.getHand().getValue());

        dealerArea.setText("");
        if (revealDealer) {
            for (Card card : dealer.getHand().getCards()) {
                dealerArea.append(card.toString() + "\n");
            }
            dealerArea.append("Wert: " + dealer.getHand().getValue());
        } else {
            Card first = dealer.getHand().getCards().get(0);
            dealerArea.append(first.toString() + "\n");
            dealerArea.append("[Versteckte Karte]\n");
        }
    }

    private void playerHit() {
        player.getHand().addCard(deck.drawCard());
        updateHands(false);
        if (player.getHand().isBust()) {
            endRound();
        }
    }

    private void playerStand() {
        dealer.playTurn(deck);
        endRound();
    }

    /** Wann das Spiel Endet *****************************************************************************************************************************************************/
    private void endRound() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(true);
        updateHands(true);

        int playerValue = player.getHand().getValue();
        int dealerValue = dealer.getHand().getValue();
        boolean playerBJ = player.getHand().isBlackjack();
        boolean dealerBJ = dealer.getHand().isBlackjack();

        if (playerBJ && !dealerBJ) {
            statusLabel.setText("Blackjack! Sie gewinnen 1.5x Einsatz.");
            player.winBet(true);
        } else if (dealerBJ && !playerBJ) {
            statusLabel.setText("Dealer hat Blackjack. Sie verlieren.");
            player.loseBet();
        } else if (player.getHand().isBust()) {
            statusLabel.setText("Sie sind überkauft. Sie verlieren.");
            player.loseBet();
        } else if (dealer.getHand().isBust()) {
            statusLabel.setText("Dealer ist überkauft. Sie gewinnen.");
            player.winBet(false);
        } else if (playerValue > dealerValue) {
            statusLabel.setText("Sie gewinnen.");
            player.winBet(false);
        } else if (playerValue < dealerValue) {
            statusLabel.setText("Sie verlieren.");
            player.loseBet();
        } else {
            statusLabel.setText("Unentschieden. Ihr Einsatz wird zurückgegeben.");
            player.pushBet();
        }
        balanceLabel.setText("Guthaben: " + player.getBalance() + "€");
    }

    private void resetRound() {
        if (player.getBalance() <= 0) {
            JOptionPane.showMessageDialog(this, "Kein Guthaben mehr. Spiel vorbei.");
            System.exit(0);
        }
        statusLabel.setText("Bitte setzen Sie Ihren Einsatz und drücken Sie 'Setzen'.");
        playerArea.setText("");
        dealerArea.setText("");
        betField.setText("");
        betButton.setEnabled(true);
        betField.setEnabled(true);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(false);
    }

    // Wählt je nach Gewinn oder Verlust die passende Animation
    private void showExitDialog() {
        int gain = player.getBalance() - initialBalance;
        if (gain >= 0) {
            showWinDialog(gain);
        } else {
            showLossDialog(-gain);
        }
    }

    // Startet Gewinn-Dialog
    private void showWinDialog(int gain) {
        JDialog dialog = createDialog("Sie haben gewonnen!", "Gewinn: 0€", gain, true);
        dialog.setVisible(true);
        dialog.setBackground(getForeground());
    }

    // Startet Verlust-Dialog
    private void showLossDialog(int loss) {
        JDialog dialog = createDialog("Sie haben verloren!", "Verlust: 0€", loss, false);
        dialog.setVisible(true);
    }

    // Erzeugt Dialog und animiert Zahl bis zum Zielwert
    private JDialog createDialog(String title, String startText, int target, boolean isWin) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel animLabel = new JLabel(startText, SwingConstants.CENTER);
        animLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        dialog.add(animLabel, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Exit");
        closeBtn.setFont(new Font("Verdana", Font.BOLD, 16));
        closeBtn.setPreferredSize(new Dimension(80, 40));
        closeBtn.addActionListener((ActionEvent e) -> System.exit(0));
        JPanel south = new JPanel();
        south.add(closeBtn);
        dialog.add(south, BorderLayout.SOUTH);

        Timer timer = new Timer(1, null);
        timer.addActionListener(e -> {
            String text = animLabel.getText().replaceAll("[^0-9]", "");
            int current = Integer.parseInt(text);
            if (current < target) {
                current ++;
                animLabel.setText((isWin ? "Gewinn: " : "Verlust: ") + current + "€");
            } else {
                timer.stop();
            }
        });
        timer.start();
        return dialog;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}
