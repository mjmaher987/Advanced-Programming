package Bank;

public class ATM {
    private Card card;

    public boolean isNoCardInserted() {
        return card == null;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
