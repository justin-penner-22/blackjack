package blackjack;
import java.util.*;

/** Repräsentiert die Spielerkarten **************************************************************************************************************************************************************/
public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getValue() { // Berechnet den Blackjack-Wert der Hand
        int value = 0;
        int aceCount = 0;
        for (Card c : cards) {
            value += c.getValue();
            if (c.getRank().equals("A")) {
                aceCount++;
            }
        }
        
        while (value > 21 && aceCount > 0) { // Adjust for aces
            value -= 10;
            aceCount--;
        }
        return value;
    }

    public boolean isBust() {
        return getValue() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    public void clear() {
        cards.clear();
    }
}
