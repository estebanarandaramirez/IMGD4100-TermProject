import java.util.ArrayList;

public class Deck {
    public ArrayList<Card> cards;
    public DiscardPile discardPile;
    static final int DECK_SIZE = 108;

    public Deck() {
        cards = new ArrayList<Card>();
        discardPile = new DiscardPile();

        for(Values value : Values.values()) {
            for (Colors color : Colors.values()) {
                switch (value) {
                    case Zero:
                        if (color != Colors.Wild) {
                            cards.add(new Card(value, color));
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
                                cards.add(new Card(value, color));
                            }
                        }
                        break;
                    case DrawFour:
                    case ChangeColor:
                        if (color == Colors.Wild) {
                            for (int i = 0; i < 4; i++) {
                                cards.add(new Card(value, color));
                            }
                        }
                        break;
                }
            }
        }
    }


    public boolean playCard(Card c) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).equals(c)){
                cards.remove(i);
                discardPile.cards.add(c);
                return true;
            }
        }
        return false;
    }

    public boolean drawCard(Card c) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).equals(c)){
                cards.remove(i);
                return true;
            }
        }
        return false;
    }

    public void reshuffle() {
        while (discardPile.cards.size() != 1) {
            this.cards.add(discardPile.cards.get(0));
            discardPile.cards.remove(0);
        }
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
