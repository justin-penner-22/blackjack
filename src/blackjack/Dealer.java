package blackjack;
public class Dealer {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    }

    public void playTurn(Deck deck) {
        while (hand.getValue() < 17) {
            hand.addCard(deck.drawCard());
        }
    }
}

    