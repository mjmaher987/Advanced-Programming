import java.util.HashMap;

public class Confectionary {
    private static HashMap<Integer, Integer> discounts;

    static {
        discounts = new HashMap<>();
    }

    private int balance;

    public Confectionary() {
        setBalance(0);
    }

    public static boolean isDiscountExists(int code) {
        for (Integer key1 : discounts.keySet()) {
            if (key1 == code) {
                return true;
            }
        }
        return false;
    }

    public static void addDiscount(int code, int price) {
        discounts.put(code, price);
    }

    public static int getDiscountPriceByCode(int code) {
        return discounts.get(code);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void increaseBalance(int balance) {
        setBalance(this.getBalance() + balance);
    }

}
