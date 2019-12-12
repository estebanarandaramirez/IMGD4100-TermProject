import java.util.ArrayList;

public class Hand {

    int numCards;
    ArrayList<Card> cardsCantPlayOn;

    public Hand(int startingNum) {
        numCards = startingNum;
    }

    public void draw(int numToDraw) {
        numCards += numToDraw;
    }

    public void discard() {
        numCards--;
    }

    public boolean hasUno() {
        return numCards == 1;
    }

    public boolean hasWon() {
        return numCards == 0;
    }

    public void addCannotPlayCard(Card c) {
        cardsCantPlayOn.add(c);
    }

    public void refreshCantPlayOn() {
        while (cardsCantPlayOn.size() != 0) {
            cardsCantPlayOn.remove(0);
        }
    }
    
    public int handSize() {
    	return numCards;
    }
}
