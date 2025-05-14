public class Card {
    private final String rank;

    public Card(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        switch (rank) {
            case "A": return 1;
            case "K":
            case "Q":
            case "J": return 10;
            default: return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        return rank;
    }
}
