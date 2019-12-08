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

    public Card convertToCard(String card) {
        String[] attributes = card.split("-");
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

        return new Card(value, color);
    }
}
