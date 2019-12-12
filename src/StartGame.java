import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class StartGame {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input;
        Card card;
        ArrayList<Hand> playerHands;
        int value;
        boolean prevActionDrawOne = false;
        int turn = 1;

        System.out.println("Welcome to UNO bot.");
        System.out.println("Input each player's actions at each turn.");
        System.out.println("Players 0, 1, and 2 represent opponents. Player 3 is the user.");
        System.out.println("Instructions:");
        System.out.println("\tType 'value-color' of a card to represent that it has been played. For example, 'drawfour-wild' or '9-yellow'.");
        System.out.println("\tType 'pass' when a player does not play any card.");
        System.out.println("\tType 'draw' followed by a number to represent the number of cards drawn by an opponent.");
        System.out.println("\tType 'draw' followed by a card(s) to represent the cards you drew.");
        System.out.println("\tTo repeat the instructions type 'help'. To terminate the program type 'exit'.");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Input starting player: ");
        input = in.nextLine();
        while(Integer.valueOf(input) < 0 || Integer.valueOf(input) > 3) {
            System.out.println("Invalid starting player");
            System.out.println("Input starting player: ");
            input = in.nextLine();
        }
        Game game = new Game(Integer.valueOf(input));

        System.out.println("Input cards in starting hand: ");
        input = in.nextLine();
        boolean flag = true;
        while(flag) {
            flag = false;
            String[] cards = input.split(" ");
            if(cards.length != game.HAND_SIZE) {
                System.out.println("Invalid number of cards");
                flag = true;
            }
            Card[] cardsToAdd = new Card[cards.length];
            for(int i = 0; i < cards.length; i++) {
                cardsToAdd[i] = game.convertToCard(cards[i]);
                if(cardsToAdd[i].getValue() == null || cardsToAdd[i].getColor() == null) {
                    System.out.println("Invalid card(s)");
                    flag = true;
                }
            }
            if(!flag) {
                for(int i = 0; i < cardsToAdd.length; i++) {
                    if(game.deck.hasCard(cardsToAdd[i]))
                        game.userDrawCard(cardsToAdd[i]);
                    else {
                        flag = true;
                        int length = game.userHand.handSize();
                        for (int j = 0; j < length; j++) {
                            game.userHand.removeCard(cardsToAdd[j]);
                            game.deck.addCard(cardsToAdd[j]);
                        }
                        System.out.println("Card(s) not in deck");
                        break;
                    }
                }
                if(!flag)
                    break;
            }
            System.out.println("Input cards in starting hand: ");
            input = in.nextLine();
        }
        System.out.println(" ");

        while(!game.gameFinished) {
            playerHands = game.players;
            System.out.printf("Opponent 0: %d cards \n", playerHands.get(0).handSize());
            System.out.printf("Opponent 1: %d cards \n", playerHands.get(1).handSize());
            System.out.printf("Opponent 2: %d cards \n", playerHands.get(2).handSize());
            System.out.printf("Your hand: %d cards \n", game.userHand.handSize());
            System.out.print("\t");
            game.userHand.printCards();
            if(game.deck.discardPile.getTopCard() == null)
                System.out.println("Top of discard pile: *empty*");
            else {
                value = game.deck.discardPile.getTopCard().getValue().ordinal();
                if(value < 10)
                    System.out.println("Top of discard pile: " + value + "-" + game.deck.discardPile.getTopCard().getColor());
                else
                    System.out.println("Top of discard pile: " + game.deck.discardPile.getTopCard().getValue() + "-" + game.deck.discardPile.getTopCard().getColor());
            }
            if(game.isUserTurn())
                System.out.printf("TURN %d - Your turn: ", turn);
            else
                System.out.printf("TURN %d - Opponent %d's turn: ", turn, game.turnCounter);
            input = in.nextLine();

            // 'exit' command
            if(input.equals("exit"))
                System.exit(0);

            // 'help' command
            else if(input.equals("help")) {
                System.out.println("Input each player's actions at each turn.");
                System.out.println("Players 0, 1, and 2 represent opponents. Player 3 is the user.");
                System.out.println("Instructions:");
                System.out.println("\tType 'value-color' of a card to represent that it has been played. For example, 'DrawFour-wild' or '9-yellow'.");
                System.out.println("\tType 'pass' when a player does not play any card.");
                System.out.println("\tType 'draw' followed by a number to represent the number of cards drawn by an opponent.");
                System.out.println("\tType 'draw' followed by a card(s) to represent the cards you drew.");
                System.out.println("\tTo repeat the instructions type 'help'. To terminate the program type 'exit'.");

            // pass turn
            } else if(input.equals("pass")) {
                prevActionDrawOne = false;
                turn++;
                if(game.isUserTurn())
                    game.userPass();
                else
                    game.opponentPass();

            // draw cards
            } else if(input.split(" ")[0].equals("draw") && input.split(" ").length > 1) {
                if(game.isUserTurn() && !prevActionDrawOne) {
                    flag = true;
                    while(flag) {
                        flag = false;
                        String[] cards = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);
                        Card[] cardsToAdd = new Card[cards.length];
                        for(int i = 0; i < cards.length; i++) {
                            cardsToAdd[i] = game.convertToCard(cards[i]);
                            if(cardsToAdd[i].getValue() == null || cardsToAdd[i].getColor() == null) {
                                System.out.println("Invalid card(s)");
                                flag = true;
                            }
                        }
                        if(!flag) {
                            int length;
                            for(int i = 0; i < cardsToAdd.length; i++) {
                                if(game.deck.hasCard(cardsToAdd[i]))
                                    game.userDrawCard(cardsToAdd[i]);
                                else {
                                    flag = true;
                                    length = i;
                                    for (int j = 0; j < length; j++) {
                                        game.userHand.removeCard(cardsToAdd[j]);
                                        game.deck.addCard(cardsToAdd[j]);
                                    }
                                    System.out.println("Card(s) not in deck");
                                    break;
                                }
                            }
                            if(!flag) {
                                prevActionDrawOne = true;
                                if(cardsToAdd.length > 1) {
                                    turn++;
                                    game.advanceTurn();
                                }
                                break;
                            }
                        }
                        if(game.isUserTurn())
                            System.out.printf("TURN %d - Your turn: ", turn);
                        else
                            System.out.printf("TURN %d - Opponent %d's turn: ", turn, game.turnCounter);

                        input = in.nextLine();
                    }
                } else if(!game.isUserTurn() && !prevActionDrawOne) {
                    if (input.split(" ").length == 2) {
                        prevActionDrawOne = true;
                        game.opponentDrawCards(game.turnCounter, Integer.valueOf(input.split(" ")[1]));
                        if (Integer.valueOf(input.split(" ")[1]) > 1)
                            turn++;
                    } else
                        System.out.println("Invalid action");
                } else
                    System.out.println("Invalid action");


            // invalid draw cards
            } else if(input.split(" ")[0].equals("draw") && (input.split(" ").length == 1 || prevActionDrawOne)) {
                System.out.println("Invalid action");

            // play card
            } else {
                prevActionDrawOne = false;
                turn++;
                card = game.convertToCard(input);
                if(card.getValue() == null || card.getColor() == null) {
                    System.out.println("Invalid card");
                } else {
                    if(game.isUserTurn())
                        game.userPlayCard(card);
                    else
                        game.opponentPlayCard(card);
                }
            }
            System.out.println(" ");
        }

        System.out.printf("PLAYER %d WON THE MATCH!!!\n", game.hasWon());
        if(game.hasWon() == 3)
            System.out.println("UNO bot is the best");
        else
            System.out.println("Beating UNO bot was just a fluke");

    }

}
