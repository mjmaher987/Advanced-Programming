import java.util.ArrayList;

public class Customer {
    private static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
    }

    private String name;
    private int id;
    private int balance;
    private int discountCode;

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
        this.balance = 0;
        customers.add(this);
        this.discountCode = -1;
    }

    public static Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getID() == id) {
                return customer;
            }
        }
        return new Customer("azsxdc", id);
    }

    public static boolean doesItExists(int id) {
        for (Customer customer : customers) {
            if (customer.getID() == id) return true;
        }
        return false;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public int getID() {
        return this.id;
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public int getDiscountCode() {
        return this.discountCode;
    }

    public void increaseCustomerBalance(int balance) {
        this.balance += balance;
    }

    public void decreaseBalance(int balance) {
        setBalance(this.getBalance() - balance);
    }


}
