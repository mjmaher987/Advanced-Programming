package Bank;

import java.util.ArrayList;

public class Card {
    private static ArrayList<Card> allCards = new ArrayList<Card>();
    private String cardNo;
    private String password;
    private int balance;

    public Card(String cardNo, String password) {
        this.cardNo = cardNo;
        this.password = password;
        this.balance = 0;
        addCard(this);
    }

    public static synchronized void addCard(Card card) {
        allCards.add(card);
    }

    public static Card getCardByCardNo(String cardNo) {
        for (Card card : allCards) {
            if (card.cardNo.equals(cardNo))
                return card;
        }
        return null;
    }

    public synchronized static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public String getPassword() {
        return password;
    }

    public synchronized void decreaseBalance(int amount) {
        this.balance -= amount;
    }

    public synchronized void increaseBalance(int amount) {
        this.balance += amount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNo() {
        return cardNo;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public static void eraseData() {
        Card.allCards = new ArrayList<>();
    }
}
