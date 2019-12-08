import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Deck deck = new Deck();
        Scanner in = new Scanner(System.in);
        String[] inputCards;
        Card[] cards;
        while(true) {
            String input = in.nextLine();
            inputCards = input.split(" ");
            cards = new Card[inputCards.length];
            for(int i = 0; i < inputCards.length; i++) {
                cards[i] = deck.convertToCard(inputCards[i]);
            }
            System.out.println(cards[0].getColor());
        }
    }

}
