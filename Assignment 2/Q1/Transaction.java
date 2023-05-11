import java.util.ArrayList;

public class Transaction {

    private static int idCounter;
    private static ArrayList<Transaction> transactions;

    static {
        idCounter = 1;
    }

    static {
        transactions = new ArrayList<>();
    }

    private int id;
    private int customerId;
    private int amount;
    private int discountCode;
    private int discountPrice;
    private int finalPayment;
    public int realAmount;
    private Boolean isAccepted;


    public Transaction(int customerId, int amount, int discountCode) {
        this.customerId = customerId;
        this.amount = amount;
        this.discountCode = discountCode;
        if (discountCode == -1) this.discountPrice = 0;
        else {
            discountPrice = Confectionary.getDiscountPriceByCode(discountCode);
            Customer.getCustomerById(customerId).setDiscountCode(-1);
        }
        this.isAccepted = false;
        this.id = idCounter;
        idCounter++;
        transactions.add(this);
    }

    public static Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.id == id) return transaction;
        }
        return new Transaction(0, 0, 0);
    }

    public static boolean doesItExists(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.id == id) return true;
        }
        return false;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getId() {
        return this.id;
    }

    public void setAccepted() {
        this.isAccepted = true;
    }

    public int getDiscountCode() {
        return this.discountCode;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getFinalPayment() {
        return this.finalPayment;
    }


    public void setFinalPayment(int price) {
        this.finalPayment = price;
    }

    public void exchangeMoney() {

    }

    public boolean isTransactionAccepted() {
        return this.isAccepted;
    }

}
