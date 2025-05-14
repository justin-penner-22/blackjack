import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BlackjackGUI extends JFrame {
    private final Deck deck = new Deck();
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> dealerHand = new ArrayList<>();

    private final JTextArea playerArea = new JTextArea();
    private final JTextArea dealerArea = new JTextArea();
    private final JLabel statusLabel = new JLabel("Welcome to Blackjack!");
    private final JButton hitButton = new JButton("Hit");
    private final JButton standButton = new JButton("Stand");

    public BlackjackGUI() {
        setTitle("Blackjack");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        deck.shuffle();
        dealInitialCards();

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        playerArea.setEditable(false);
        dealerArea.setEditable(false);
        centerPanel.add(new JScrollPane(playerArea));
        centerPanel.add(new JScrollPane(dealerArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);

        updateHands(false);

        hitButton.addActionListener(e -> {
            playerHand.add(deck.drawCard());
            updateHands(false);
            if (getHandValue(playerHand) > 21) {
                endGame("You bust! Dealer wins.");
            }
        });

        standButton.addActionListener(e -> {
            playDealerTurn();
        });
    }

    private void dealInitialCards() {
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
    }

    private void updateHands(boolean showDealerFull) {
        playerArea.setText("Your Hand: " + playerHand + " (Total: " + getHandValue(playerHand) + ")");
        if (showDealerFull) {
            dealerArea.setText("Dealer Hand: " + dealerHand + " (Total: " + getHandValue(dealerHand) + ")");
        } else {
            dealerArea.setText("Dealer shows: " + dealerHand.get(0) + " [?]");
        }
    }

    private void playDealerTurn() {
        updateHands(true);
        while (getHandValue(dealerHand) < 17) {
            dealerHand.add(deck.drawCard());
            updateHands(true);
        }

        int playerTotal = getHandValue(playerHand);
        int dealerTotal = getHandValue(dealerHand);

        if (dealerTotal > 21) {
            endGame("Dealer busts! You win!");
        } else if (playerTotal > dealerTotal) {
            endGame("You win!");
        } else if (playerTotal < dealerTotal) {
            endGame("Dealer wins.");
        } else {
            endGame("Push. It's a tie.");
        }
    }

    private void endGame(String result) {
        updateHands(true);
        statusLabel.setText(result);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
    }

    private int getHandValue(List<Card> hand) {
        int total = 0;
        int aces = 0;
        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("A")) aces++;
        }
        while (aces > 0 && total + 10 <= 21) {
            total += 10;
            aces--;
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI game = new BlackjackGUI();
            game.setVisible(true);
        });
    }
}
