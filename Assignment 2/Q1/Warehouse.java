import java.util.ArrayList;

public class Warehouse {

    private static ArrayList<Warehouse> warehouses;

    static {
        warehouses = new ArrayList<>();
    }

    private int amount;
    private String materialName;

    public Warehouse(String materialName, int amount) {
        this.materialName = materialName;
        setAmount(amount);
        warehouses.add(this);
    }

    public static Warehouse getWarehouseByName(String name) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.materialName.equals(name)) {
                return warehouse;
            }
        }
        return new Warehouse("aa", 6);
    }

    public static boolean doesItExists(String materialName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.materialName.equals(materialName)) return true;
        }
        return false;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void increaseMaterial(int amount) {
        setAmount(this.getAmount() + amount);
    }

    public void decreaseMaterial(int amount) {
        this.amount = this.getAmount() - amount;
    }


}
