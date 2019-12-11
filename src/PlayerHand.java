import java.util.ArrayList;

public class PlayerHand {

    private ArrayList<Card> cards;

    public PlayerHand() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public boolean removeCard(Card c) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(c)){
                cards.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean hasUno() {
        return cards.size() == 1;
    }

    public boolean hasWon() {
        return cards.size() == 0;
    }

    public boolean contains(Card c) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(c)) {
                return true;
            }
        }
        return false;
    }
}
