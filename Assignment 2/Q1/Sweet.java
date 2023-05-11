import java.util.ArrayList;
import java.util.HashMap;

public class Sweet {

    private static ArrayList<Sweet> sweets;

    static {
        sweets = new ArrayList<>();
    }

    private String name;
    private int price;
    private int amount;
    private HashMap<String, Integer> materials = new HashMap<>();


    public Sweet(String name, int price, HashMap materials) {
        this.name = name;
        this.price = price;
        this.materials = materials;
        sweets.add(this);
        setAmount(0);
    }

    public HashMap getMaterials() {
        return materials;
    }

    public String getName() {
        return name;
    }


    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return this.price;
    }


    public static Sweet getSweetByName(String name) {
        for (Sweet sweet : sweets) {
            if (sweet.name.equals(name)) {
                return sweet;
            }
        }
        return new Sweet("a", 2, new HashMap<String, Integer>());
    }

    public void decreaseMaterialOfSweetFromWarehouse(int amount) {
        for (Object key1 : this.getMaterials().keySet()) {
            int neededForEachSweet = (int) this.getMaterials().get(key1);
            Warehouse.getWarehouseByName((String) key1).decreaseMaterial(amount * neededForEachSweet);
        }
    }

    public void increaseSweet(int amount) {
        int currentSweet = this.amount;
        setAmount(currentSweet + amount);
    }

    public static boolean doesItExists(String name) {
        for (Sweet sweet : sweets) {
            if (name.equals(sweet.getName())) return true;
        }
        return false;
    }


}
