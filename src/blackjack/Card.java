package blackjack;
public class Card {
    public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
    private Suit suit;
    private String rank;
    private int value;

    public Card(Suit suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}