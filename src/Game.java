import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Deck deck = new Deck();
        Scanner in = new Scanner(System.in);
        String[] inputCards;
        Card[] cards;
        boolean start = true;
        int turn = 1;

        while(true) {
            if(start) {
                start = false;
                System.out.println("Welcome to UNO bot");
                System.out.println("Input each player's actions at each turn");
                System.out.println("To terminate the program type 'exit'");
            }
            System.out.printf("Turn %d: ", turn);
            String input = in.nextLine();
            if(input.equals("exit"))
                System.exit(0);

            inputCards = input.split(" ");
            cards = new Card[inputCards.length];
            for(int i = 0; i < inputCards.length; i++) {
                cards[i] = deck.convertToCard(inputCards[i]);
                if(cards[i].getValue() == null || cards[i].getColor() == null) {
                    System.out.println("Invalid card(s)");
                    break;
                }
            }
        }
    }

}
