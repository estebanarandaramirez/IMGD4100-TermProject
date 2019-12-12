import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import java.util.ArrayList;

public class Game {

    public static final int HAND_SIZE = 7;
    public static final int NUM_OPPONENTS = 3;

    Deck deck;
    ArrayList<Hand> players;
    PlayerHand userHand;
    int turnCounter;
    boolean gameFinished;
    boolean isReversed;
    int winningPlayer;

    public Game(int startingPlayer) {

        Deck deck = new Deck();
        ArrayList<Hand> players = new ArrayList<Hand>();
        PlayerHand userHand = new PlayerHand();
        turnCounter = startingPlayer;
        gameFinished = false;


        for (int i = 0; i < NUM_OPPONENTS; i++) {
            players.add(new Hand(HAND_SIZE));
        }
    }

    private void advanceTurn() {
        if (!isReversed) {
            turnCounter++;
            if (turnCounter > NUM_OPPONENTS + 1) {
                turnCounter = 0;
            }
        }
        else {
            turnCounter--;
            if (turnCounter < 0) {
                turnCounter = NUM_OPPONENTS + 1;
            }
        }
    }

    public void opponentDrawCards(int playerNum, int cardsToDraw) {
        players.get(playerNum).draw(cardsToDraw);
        players.get(playerNum).refreshCantPlayOn();
    }

    public boolean userDrawCard(Card c) {
        boolean cardDrawn = deck.drawCard(c);
        if (cardDrawn) {
            userHand.addCard(c);
        }
        return cardDrawn;
    }

    public void opponentPass() {
        players.get(turnCounter).addCannotPlayCard(deck.discardPile.getTopCard());
        advanceTurn();
    }

    public void userPass() {
        advanceTurn();
    }

    public boolean opponentPlayCard(Card c) {
        boolean cardPlayed = deck.playCard(c);
        if (cardPlayed) {
            if (c.getValue().equals(Values.Reverse)) {
                isReversed = !isReversed;
            }
            else if (c.getValue().equals(Values.Skip)) {
                advanceTurn();
            }
            if (players.get(turnCounter).hasUno()) {
                System.out.println("Player " + turnCounter + " has UNO!");
            }
            if (players.get(turnCounter).hasWon()) {
                System.out.println("Player " + turnCounter + " has won!");
                gameFinished = true;
                winningPlayer = turnCounter;
            }
            advanceTurn();
        }
        return cardPlayed;
    }

    public boolean userPlayCard(Card c) {
        boolean validCard = userHand.contains(c);
        if (validCard) {
            if (players.get(turnCounter).hasUno()) {
                System.out.println("You have UNO!");
            }
            if (players.get(turnCounter).hasWon()) {
                System.out.println("You have won!");
                gameFinished = true;
                winningPlayer = turnCounter;
            }
            if (c.getValue().equals(Values.Reverse)) {
                isReversed = !isReversed;
            }
            else if (c.getValue().equals(Values.Skip)) {
                advanceTurn();
            }
            advanceTurn();
        }
        return validCard;
    }

    public int hasWon() {
        if (gameFinished){
            return winningPlayer;
        }
        return -1;
    }

    public boolean isUserTurn() {
        return turnCounter == 0;
    }

    public Card convertToCard(String c) {
        String[] attributes = c.split("-");
        if(attributes.length != 2)
            return new Card(null, null);

        Values value;
        switch (attributes[0]) {
            case "1":
                value = Values.One;
                break;
            case "2":
                value = Values.Two;
                break;
            case "3":
                value = Values.Three;
                break;
            case "4":
                value = Values.Four;
                break;
            case "5":
                value = Values.Five;
                break;
            case "6":
                value = Values.Six;
                break;
            case "7":
                value = Values.Seven;
                break;
            case "8":
                value = Values.Eight;
                break;
            case "9":
                value = Values.Nine;
                break;
            case "Skip": case "skip": case "SKIP":
                value = Values.Skip;
                break;
            case "DrawTwo": case "drawtwo": case "DRAWTWO":
                value = Values.DrawTwo;
                break;
            case "Reverse": case "reverse": case "REVERSE":
                value = Values.Reverse;
                break;
            case "ChangeColor": case "changecolor": case "CHANGECOLOR":
                value = Values.ChangeColor;
                break;
            case "DrawFour": case "drawfour": case "DRAWFOUR":
                value = Values.DrawFour;
                break;
            default:
                value = null;
        }
        Colors color;
        switch (attributes[1]) {
            case "Red": case "red": case "RED":
                color = Colors.Red;
                break;
            case "Yellow": case "yellow": case "YELLOW":
                color = Colors.Yellow;
                break;
            case "Green": case "green": case "GREEN":
                color = Colors.Green;
                break;
            case "Blue": case "blue": case "BLUE":
                color = Colors.Blue;
                break;
            case "Wild": case "wild": case "WILD":
                color = Colors.Wild;
                break;
            default:
                color = null;
        }

        if((value == Values.ChangeColor && color != Colors.Wild) || (value == Values.DrawFour && color != Colors.Wild) || (value != Values.ChangeColor && value != Values.DrawFour && color == Colors.Wild)) {
            value = null;
            color = null;
        }

        return new Card(value, color);
    }
}
