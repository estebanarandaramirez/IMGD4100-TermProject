import java.util.ArrayList;
import java.util.Scanner;

public class StartGame {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input;
        Card card;
        ArrayList<Hand> playerHands;
        int turn = 1;

        System.out.println("Welcome to UNO bot.");
        System.out.println("Input each player's actions at each turn.");
        System.out.println("Players 0, 1, and 2 represent opponents. Player 3 is the user.");
        System.out.println("To get more info about the match type 'game status'. To terminate the program type 'exit'.");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Input starting player: ");
        input = in.nextLine();
        while(Integer.valueOf(input) < 0 || Integer.valueOf(input) > 3) {
            System.out.println("Invalid starting player");
            System.out.println("Input starting player: ");
            input = in.nextLine();
        }
        Game game = new Game(Integer.valueOf(input));

        while(true) {
            System.out.printf("Turn %d: ", turn);
            input = in.nextLine();
            if(input.equals("exit"))
                System.exit(0);
            else if(input.equals("game stauts")) {
                playerHands = game.players;
                System.out.printf("Opponent 0: %d cards", playerHands.get(0).numCards);
                System.out.printf("Opponent 1: %d cards", playerHands.get(1).numCards);
                System.out.printf("Opponent 2: %d cards", playerHands.get(2).numCards);
            } else if(input.equals("pass")) {
                if(game.isUserTurn())
                    game.userPass();
                else
                    game.opponentPass();
            } else if(input.split(" ")[0].equals("draw")) {
                if(game.isUserTurn())
                    game.userDrawCard(Integer.valueOf(input.split(" ")[1]));
                else
                    game.opponentDrawCards(game.turnCounter, Integer.valueOf(input.split(" ")[1]));
            } else {
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
        }

    }

}
