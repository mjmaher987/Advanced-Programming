package model.primitive;

public class Stationery {
    public int price;
    private Type type;

    public Stationery() {
    }

    public Stationery(int price, Type type) {
        this.price = price;
        this.type = type;
    }

    public enum Type {
        PEN,
        PENCIL
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
