import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramController {
    public static Confectionary confectionary;

    static {
        confectionary = new Confectionary();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;
        boolean nowWeCanStartScannig = false;
        while (continueProgram) {
            String scanning = scanner.nextLine();
            scanning = scanning.trim();
            if (scanning.equals("end")) continueProgram = false;
            else if (scanning.equals("create confectionary") && !nowWeCanStartScannig) {
                nowWeCanStartScannig = true;
                Confectionary confectionary = new Confectionary();
            } else if (nowWeCanStartScannig) {
                checkInput(scanning);
            } else {
                System.out.println("invalid command");
            }
        }

    }

    private void checkInput(String scanning) {
        Matcher matcher4AddCustomer = getCommandMatcher(scanning, "^add customer id (\\d+) name ([a-zA-Z ]+)$");
        Matcher matcher4ChargeCustomerBalance = getCommandMatcher(scanning, "^increase balance customer (\\d+) amount (\\d+)$");
        Matcher matcher4AddWarehouse = getCommandMatcher(scanning, "^add warehouse material ([a-zA-Z ]+) amount (\\d+)$");
        Matcher matcher4IncreaseWarehouseMaterial = getCommandMatcher(scanning, "^increase warehouse material ([a-zA-Z ]+) amount (\\d+)$");
        Matcher matcher4AddSweet = getCommandMatcher(scanning, "^add sweet name ([a-zA-Z ]+) price (\\d+) materials: ([a-zA-Z ]+) (\\d+)(.+)$");
        Matcher matcher4UnusualAddSweet = getCommandMatcher(scanning, "^add sweet name ([a-zA-Z ]+) price (\\d+) materials:$");
        Matcher matcher4IncreaseSweet = getCommandMatcher(scanning, "^increase sweet ([a-zA-Z ]+) amount (\\d+)$");
        Matcher matcher4AddDiscount = getCommandMatcher(scanning, "^add discount code (\\d+) price (\\d+)$");
        Matcher matcher4AddDiscountToCustomer = getCommandMatcher(scanning, "^add discount code code (\\d+) to customer id (\\d+)$");
        Matcher matcher4SellSweet = getCommandMatcher(scanning, "^sell sweet ([a-zA-Z ]+) amount (\\d+) to customer (\\d+)$");
        Matcher matcher4AcceptTransaction = getCommandMatcher(scanning, "^accept transaction (\\d+)$");
        Matcher matcher4PrintTransactions = getCommandMatcher(scanning, "^print transactions list$");
        Matcher matcher4PrintInCome = getCommandMatcher(scanning, "^print income$");
        if (matcher4AddCustomer.find()) addCustomer(matcher4AddCustomer);
        else if (matcher4ChargeCustomerBalance.find()) chargeCustomerBalance(matcher4ChargeCustomerBalance);
        else if (matcher4AddWarehouse.find()) addWarehouse(matcher4AddWarehouse);
        else if (matcher4IncreaseWarehouseMaterial.find()) increaseWarehouseMaterial(matcher4IncreaseWarehouseMaterial);
        else if (matcher4AddSweet.find()) addSweet(matcher4AddSweet);
        else if (matcher4UnusualAddSweet.find()) unsualAddSweet(matcher4UnusualAddSweet);
        else if (matcher4IncreaseSweet.find()) increaseSweet(matcher4IncreaseSweet);
        else if (matcher4AddDiscount.find()) addDiscount(matcher4AddDiscount);
        else if (matcher4AddDiscountToCustomer.find()) addDiscountToCustomer(matcher4AddDiscountToCustomer);
        else if (matcher4SellSweet.find()) sellSweet(matcher4SellSweet);
        else if (matcher4AcceptTransaction.find()) acceptTransaction(matcher4AcceptTransaction);
        else if (matcher4PrintTransactions.find()) printTransactions(matcher4PrintTransactions);
        else if (matcher4PrintInCome.find()) printInCome(matcher4PrintInCome);
        else {
            System.out.println("invalid command");
        }
    }

    private void addCustomer(Matcher matcher) {
        String customerName = matcher.group(2);
        int customerId = Integer.parseInt(matcher.group(1));
        if (!Customer.doesItExists(customerId)) {
            Customer customer = new Customer(customerName, customerId);
        } else {
            System.out.println("customer with this id already exists");
        }
    }

    private void chargeCustomerBalance(Matcher matcher) {
        int customerId = Integer.parseInt(matcher.group(1));
        int amount = Integer.parseInt(matcher.group(2));
        if (!Customer.doesItExists(customerId)) {
            System.out.println("customer not found");
        } else {
            Customer.getCustomerById(customerId).increaseCustomerBalance(amount);
        }
    }

    private void addWarehouse(Matcher matcher) {
        String materialName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        if (Warehouse.doesItExists(materialName)) {
            System.out.println("warehouse having this material already exists");
        } else {
            Warehouse warehouse = new Warehouse(materialName, amount);
        }
    }

    private void increaseWarehouseMaterial(Matcher matcher) {
        String materialName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        if (!Warehouse.doesItExists(materialName)) {
            System.out.println("warehouse not found");
        } else {
            Warehouse.getWarehouseByName(materialName).increaseMaterial(amount);
        }
    }

    private void addSweet(Matcher matcher) {
        ArrayList<String> notFoundWarehouses = new ArrayList<>();
        if (isItValid(matcher.group(5))) {
            if (!Warehouse.doesItExists(matcher.group(3))) {
                notFoundWarehouses.add(matcher.group(3));
            }
            String group = matcher.group(5);
            HashMap<String, Integer> neededMaterials = new HashMap<>();
            neededMaterials.put(matcher.group(3), Integer.parseInt(matcher.group(4)));
            Matcher matcher2 = getCommandMatcher(group, ", ([a-zA-Z ]+) (\\d+)");
            while (matcher2.find()) {
                neededMaterials.put(matcher2.group(1), Integer.parseInt(matcher2.group(2)));
                if (!Warehouse.doesItExists(matcher2.group(1))) {
                    notFoundWarehouses.add(matcher2.group(1));
                }
            }
            if (notFoundWarehouses.size() != 0) {
                System.out.print("not found warehouse(s):");
                for (String notFoundWarehouse1 : notFoundWarehouses) {
                    System.out.print(" " + notFoundWarehouse1);
                }
                System.out.println();
            } else {
                Sweet sweet = new Sweet(matcher.group(1), Integer.parseInt(matcher.group(2)), neededMaterials);
            }
        } else {
            System.out.println("invalid command");
        }
    }

    private void unsualAddSweet(Matcher matcher4UnusualAddSweet) {
        HashMap<String, Integer> neededMaterials = new HashMap<>();
        Sweet sweet = new Sweet(matcher4UnusualAddSweet.group(1), Integer.parseInt(matcher4UnusualAddSweet.group(2)), neededMaterials);
    }

    private boolean isItValid(String group) {
        int commaCounter = 0;
        for (int i = 0; i < group.length(); i++) {
            if (group.charAt(i) == ',') {
                commaCounter++;
            }
        }
        Matcher matcher = getCommandMatcher(group, ", ([a-zA-Z ]+) (\\d+)");
        int matcherCounter = 0;
        while (matcher.find()) {
            matcherCounter++;
        }
        return (matcherCounter == commaCounter);
    }

    private void increaseSweet(Matcher matcher) {
        String sweetName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        if (!Sweet.doesItExists(sweetName)) {
            System.out.println("sweet not found");
        } else {
            ArrayList<String> insufficientMaterials = new ArrayList<>();
            for (Object key1 : Sweet.getSweetByName(sweetName).getMaterials().keySet()) {
                int neededForEachSweet = (int) Sweet.getSweetByName(sweetName).getMaterials().get(key1);
                if (Warehouse.getWarehouseByName((String) key1).getAmount() < amount * neededForEachSweet) {
                    insufficientMaterials.add((String) key1);
                }
            }
            if (insufficientMaterials.size() != 0) {
                System.out.print("insufficient material(s):");
                for (String notEnough : insufficientMaterials) {
                    System.out.print(" " + notEnough);
                }
                System.out.println();
            } else {
                Sweet.getSweetByName(sweetName).decreaseMaterialOfSweetFromWarehouse(amount);
                Sweet.getSweetByName(sweetName).increaseSweet(amount);
            }
        }
    }

    private void addDiscount(Matcher matcher) {
        int code = Integer.parseInt(matcher.group(1));
        int price = Integer.parseInt(matcher.group(2));
        if (Confectionary.isDiscountExists(code)) {
            System.out.println("discount with this code already exists");
        } else {
            Confectionary.addDiscount(code, price);
        }
    }

    private void addDiscountToCustomer(Matcher matcher) {
        int code = Integer.parseInt(matcher.group(1));
        int id = Integer.parseInt(matcher.group(2));
        if (!Confectionary.isDiscountExists(code)) {
            System.out.println("discount code not found");
        } else if (!Customer.doesItExists(id)) {
            System.out.println("customer not found");
        } else {
            Customer.getCustomerById(id).setDiscountCode(code);
        }
    }

    private void sellSweet(Matcher matcher) {
        String sweetName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        int customerId = Integer.parseInt(matcher.group(3));
        if (notValidSweet(sweetName)) System.out.println("sweet not found");
        else if (insufficientSweet(sweetName, amount)) System.out.println("insufficient sweet");
        else if (invalidCustomer(customerId)) System.out.println("customer not found");
        else if (insufficientCustomerMoney(customerId, sweetName, amount))
            System.out.println("customer has not enough money");
        else {
            makeNewTransaction(customerId, sweetName, amount);
        }
    }

    private void makeNewTransaction(int customerId, String sweetName, int amount) {
        int final1 = 0;
        int realMoney = 0;
        if (Customer.getCustomerById(customerId).getDiscountCode() == -1) {
            final1 = Sweet.getSweetByName(sweetName).getPrice() * amount;
            realMoney = final1;
        } else {
            final1 = Sweet.getSweetByName(sweetName).getPrice() * amount - Confectionary.getDiscountPriceByCode
                    (Customer.getCustomerById(customerId).getDiscountCode());
            realMoney = Sweet.getSweetByName(sweetName).getPrice() * amount;
            if (final1 < 0) final1 = 0;
        }
        Transaction transaction = new Transaction(customerId, amount, Customer.getCustomerById(customerId).getDiscountCode());
        transaction.setFinalPayment(final1);
        transaction.realAmount = realMoney;
        Sweet.getSweetByName(sweetName).setAmount(Sweet.getSweetByName(sweetName).getAmount() - amount);
        System.out.println("transaction " + transaction.getId() + " successfully created");
    }

    private boolean notValidSweet(String sweetName) {
        return !Sweet.doesItExists(sweetName);
    }

    private boolean insufficientSweet(String sweetName, int amount) {
        return Sweet.getSweetByName(sweetName).getAmount() < amount;
    }

    private boolean invalidCustomer(int customerId) {
        return !Customer.doesItExists(customerId);
    }

    private boolean insufficientCustomerMoney(int id, String sweetName, int amount) {
        if (Customer.getCustomerById(id).getDiscountCode() == -1) {
            return Customer.getCustomerById(id).getBalance() < Sweet.getSweetByName(sweetName).getPrice() * amount;
        }
        return Customer.getCustomerById(id).getBalance() + Confectionary.getDiscountPriceByCode
                (Customer.getCustomerById(id).getDiscountCode()) < Sweet.getSweetByName(sweetName).getPrice() * amount;
    }

    private void acceptTransaction(Matcher matcher) {
        int transactionId = Integer.parseInt(matcher.group(1));
        if (!Transaction.doesItExists(transactionId))
            System.out.println("no waiting transaction with this id was found");
        else {
            if (Transaction.getTransactionById(transactionId).isTransactionAccepted())
                System.out.println("no waiting transaction with this id was found");
            else {
                Transaction.getTransactionById(transactionId).setAccepted();
                Customer.getCustomerById(Transaction.getTransactionById(transactionId).getCustomerId()).
                        decreaseBalance(Transaction.getTransactionById(transactionId).getFinalPayment());
                confectionary.increaseBalance(Transaction.getTransactionById(transactionId).getFinalPayment());
            }
        }
    }

    private void printTransactions(Matcher matcher) {
        boolean continueTransactions = true;
        int id = 1;
        while (continueTransactions) {
            if (Transaction.doesItExists(id)) {
                if (Transaction.getTransactionById(id).isTransactionAccepted()) {
                    System.out.print("transaction ");
                    System.out.print(id + ": ");
                    int customerId = Transaction.getTransactionById(id).getCustomerId();
                    System.out.print(customerId + " ");
                    int transactionDiscountCode = Transaction.getTransactionById(id).getDiscountCode();
                    System.out.print(Transaction.getTransactionById(id).realAmount + " ");
                    System.out.print(Transaction.getTransactionById(id).getDiscountCode() + " ");
                    System.out.println(Transaction.getTransactionById(id).getFinalPayment());
                }
            } else {
                continueTransactions = false;
            }
            id++;
        }
    }

    private void printInCome(Matcher matcher) {
        System.out.println(confectionary.getBalance());
    }

    private Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }


}
