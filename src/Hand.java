import java.util.ArrayList;

public class Hand {

    int numCards;
    ArrayList<Card> cardsCantPlayOn;

    public Hand(int startingNum) {
        numCards = startingNum;
        cardsCantPlayOn = new ArrayList<Card>();
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
        if(c != null)
            cardsCantPlayOn.add(c);
    }

    public void refreshCantPlayOn() {
        cardsCantPlayOn = new ArrayList<Card>();
    }
    
    public int handSize() {
    	return numCards;
    }

}
