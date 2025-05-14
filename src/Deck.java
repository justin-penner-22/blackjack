import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public Deck() {
        // Add 4 of each rank (ignoring suits)
        for (String rank : ranks) {
            for (int i = 0; i < 4; i++) {
                cards.add(new Card(rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}
