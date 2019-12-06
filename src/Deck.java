public class Deck {
    Card[] cards;

    public Deck() {
        cards = new Card[108];

        int counter = 0;
        for(Values value : Values.values()) {
            for (Colors color : Colors.values()) {
                switch (value) {
                    case Zero:
                        if (color != Colors.Wild) {
                            cards[counter] = new Card(value, color);
                            counter++;
                        }
                        break;
                    case One:
                    case Two:
                    case Three:
                    case Four:
                    case Five:
                    case Six:
                    case Seven:
                    case Eight:
                    case Nine:
                    case Skip:
                    case DrawTwo:
                    case Reverse:
                        if (color != Colors.Wild) {
                            for (int i = 0; i < 2; i++) {
                                cards[counter] = new Card(value, color);
                                counter++;
                            }
                        }
                        break;
                    case DrawFour:
                    case ChangeColor:
                        if (color == Colors.Wild) {
                            for (int i = 0; i < 4; i++) {
                                cards[counter] = new Card(value, color);
                                counter++;
                            }
                        }
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Deck();
    }
}
