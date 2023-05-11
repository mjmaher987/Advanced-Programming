package model.man;

import model.primitive.Stationery;
import model.zoo.Animal;

public class Son extends SimplePerson {
    private int height;
    private final Stationery stationery = new Stationery(264, Stationery.Type.PEN);
    private static final int INT;

    static {
        INT = 5;
    }

    public Son() {}

    public Son(String name, SimplePerson father, SimplePerson mother, int birthYear, Animal pet, int height) {
        super(name, father, mother, birthYear, pet);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public Stationery getStationery() {
        return stationery;
    }

    public static int getINT() {
        return INT;
    }
}
