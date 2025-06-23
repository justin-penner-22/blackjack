package blackjack;

/** Modelliert den Dealer ******************************************************************************************************************************************/
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

    public void playTurn(Deck deck) { // Fügt eine Karte hinzu, wenn die Hand des Dealers weniger als 17 Punkte hat
        while (hand.getValue() < 17) {
            hand.addCard(deck.drawCard());
        }
    }
}