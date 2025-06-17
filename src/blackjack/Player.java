package blackjack;

/** Modelliert den Spieler ******************************************************************************************************************************************/
public class Player {
    private Hand hand;
    private int balance;
    private int currentBet;

    public Player(int startingBalance) {
        hand = new Hand();
        balance = startingBalance;
        currentBet = 0;
    }

    public Hand getHand() {
        return hand;
    }

    public int getBalance() {
        return balance;
    }

    public void adjustBalance(int amount) {
        balance += amount;
    }

    public void placeBet(int bet) { // Plaziert einen Einsatz
        if (bet > balance) {
            throw new IllegalArgumentException("Nicht genügend Guthaben für diesen Einsatz.");
        }
        currentBet = bet;
        balance -= bet;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void winBet(boolean blackjack) { // Wie viel der Spieler gewinnt
        if (blackjack) {
            balance += currentBet + currentBet * 3 / 2;
        } else {
            balance += currentBet * 2;
        }
        currentBet = 0;
    }

    public void pushBet() {
        balance += currentBet;
        currentBet = 0;
    }

    public void loseBet() {
        currentBet = 0;
    }

    public void clearHand() {
        hand.clear();
    }
}
