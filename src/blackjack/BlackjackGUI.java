package blackjack;
import javax.swing.*;

import java.awt.*;

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

    public BlackjackGUI() {
        setTitle("Blackjack Spiel");
        setSize(1000, 700); // Größe des Fensters
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new BackgroundPanel());
        initializeGame();
        initializeUI();
    }

    private void initializeGame() {
        deck = new Deck();
        player = new Player(1000); // Startguthaben 1000
        dealer = new Dealer();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Info Panel oben
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(0, 100, 0)); // Dunkleres Grün 
        balanceLabel = new JLabel("Guthaben: " + player.getBalance() + "€");
        balanceLabel.setForeground(Color.WHITE); // Weißer Text
        balanceLabel.setFont(new Font("Verdana", Font.BOLD, 20)); // Größere Schrift
        statusLabel = new JLabel("Bitte setzen Sie Ihren Einsatz und drücken Sie 'Setzen'.");
        statusLabel.setForeground(Color.WHITE); // Weißer Text
        statusLabel.setFont(new Font("Verdana", Font.PLAIN, 16)); // Größere Schrift
        topPanel.add(balanceLabel);
        topPanel.add(statusLabel);
        add(topPanel, BorderLayout.NORTH);

        // Karten Panel in der Mitte
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Abstand zwischen Panels
        cardsPanel.setBackground(new Color(0, 80, 0)); // Einheitlicher Hintergrund
        playerArea = new JTextArea();
        playerArea.setEditable(false);
        playerArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            "Spieler Hand",
            0, 0,
            new Font("Verdana", Font.BOLD, 16), // Schriftgröße erhöht
            Color.WHITE
        )); // Gestylter Rahmen
        playerArea.setBackground(new Color(0, 100, 0)); // Bereich Hintergrund
        playerArea.setForeground(Color.WHITE);
        dealerArea = new JTextArea();
        dealerArea.setEditable(false);
        dealerArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            "Dealer Hand",
            0, 0,
            new Font("Verdana", Font.BOLD, 16), // Schriftgröße erhöht
            Color.WHITE
        )); // Gestylter Rahmen
        dealerArea.setBackground(new Color(0, 100, 0)); // Bereich Hintergrund
        dealerArea.setForeground(Color.WHITE);
        cardsPanel.add(new JScrollPane(playerArea));
        cardsPanel.add(new JScrollPane(dealerArea));
        add(cardsPanel, BorderLayout.CENTER);

        // Steuerungs Panel unten
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(0, 80, 0)); // Einheitlich grün
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Zentriert und mit Abstand
        JLabel betLabel = new JLabel("Einsatz: "); // Label für Einsatz
        betLabel.setForeground(Color.WHITE); // Weißer Text 
        betLabel.setFont(new Font("Verdana", Font.BOLD, 16)); // Größere Schrift

        betField = new JTextField(10);
        betField.setFont(new Font("Verdana", Font.PLAIN, 16)); // Größere Schrift
        betButton = new JButton("Setzen");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("Neues Spiel");

        // Buttons größer und mit Farbe 
        JButton[] buttons = {betButton, hitButton, standButton, newGameButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Verdana", Font.BOLD, 16));
            btn.setPreferredSize(new Dimension(140, 40));
            btn.setBackground(new Color(0, 120, 0));
            btn.setForeground(Color.WHITE);
        }

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(false);

        controlPanel.add(new JLabel(""));  
        controlPanel.add(betLabel);
        controlPanel.add(betField);
        controlPanel.add(betButton);
        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        controlPanel.add(newGameButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Listener
        betButton.addActionListener(e -> placeBet());
        hitButton.addActionListener(e -> playerHit());
        standButton.addActionListener(e -> playerStand());
        newGameButton.addActionListener(e -> resetRound());
    }

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

    private void startRound() {
        player.clearHand();
        dealer.clearHand();
        playerArea.setText("");
        dealerArea.setText("");

        // Zwei Karten austeilen
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        updateHands(false);

        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        betButton.setEnabled(false);
        betField.setEnabled(false);
    }

    private void updateHands(boolean showDealerHoleCard) {
        playerArea.setText("");
        for (Card c : player.getHand().getCards()) {
            playerArea.append(c.toString() + "\n");
        }
        playerArea.append("Wert: " + player.getHand().getValue());

        dealerArea.setText("");
        if (showDealerHoleCard) {
            for (Card c : dealer.getHand().getCards()) {
                dealerArea.append(c.toString() + "\n");
            }
            dealerArea.append("Wert: " + dealer.getHand().getValue());
        } else {
            Card firstCard = dealer.getHand().getCards().get(0);
            dealerArea.append(firstCard.toString() + "\n");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}