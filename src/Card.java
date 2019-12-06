public class Card {
    public Values value;
    public Colors color;

    public Card(Values value, Colors color) {
        this.value = value;
        this.color = color;
    }

    public Values getValue() {
        return this.value;
    }

    public Colors getColor() {
        return this.color;
    }
}
