import java.util.*;

public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        deck.shuffle();

        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();

        // Deal initial cards
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());

        // Player's turn
        boolean playerTurn = true;
        while (playerTurn) {
            System.out.println("Your hand: " + playerHand + " (Total: " + getHandValue(playerHand) + ")");
            System.out.println("Dealer's visible card: " + dealerHand.get(0));

            if (getHandValue(playerHand) > 21) {
                System.out.println("You bust! Dealer wins.");
                return;
            }

            System.out.print("Do you want to [H]it or [S]tand? ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("h")) {
                playerHand.add(deck.drawCard());
            } else if (input.equals("s")) {
                playerTurn = false;
            } else {
                System.out.println("Invalid input.");
            }
        }

        // Dealer's turn
        System.out.println("\nDealer's hand: " + dealerHand + " (Total: " + getHandValue(dealerHand) + ")");
        while (getHandValue(dealerHand) < 17) {
            dealerHand.add(deck.drawCard());
            System.out.println("Dealer hits: " + dealerHand + " (Total: " + getHandValue(dealerHand) + ")");
        }

        int playerTotal = getHandValue(playerHand);
        int dealerTotal = getHandValue(dealerHand);

        // Determine winner
        if (dealerTotal > 21) {
            System.out.println("Dealer busts! You win!");
        } else if (playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("Push. It's a tie.");
        }
    }

    // Calculates the value of a hand (handles Ace as 1 or 11)
    private static int getHandValue(List<Card> hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("A")) aces++;
        }

        // Upgrade Aces from 1 to 11 as long as it doesn't bust
        while (aces > 0 && total + 10 <= 21) {
            total += 10;
            aces--;
        }

        return total;
    }
}
