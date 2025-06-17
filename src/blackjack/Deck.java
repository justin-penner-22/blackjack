package blackjack;
import java.util.*;

/** Initialisiert ein Deck ******************************************************************************************************************************************/
public class Deck {
    private List<Card> cards; // Liste aller Karten im Deck
    private int currentCardIndex; // Index der nächsten zu ziehenden Karte

    public Deck() {
        cards = new ArrayList<>();
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        int[] values =   { 2,   3,   4,   5,   6,   7,   8,   9,    10,   10,    10,    10,    11 };

        for (Card.Suit suit : Card.Suit.values()) { // Befüllt das Deck mit Karten
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentCardIndex = 0;
    }

    public Card drawCard() { // Zieht die nächste Karte aus dem Deck
        if (currentCardIndex >= cards.size()) {
            shuffle();
        }
        return cards.get(currentCardIndex++);
    }
}