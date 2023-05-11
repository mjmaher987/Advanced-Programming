package model.man;

import model.zoo.Animal;

public class SimplePerson {
    private String name;
    private SimplePerson Father;
    private SimplePerson Mother;
    private int birthYear;
    private Animal pet;

    public SimplePerson() {}

    public SimplePerson(String name, SimplePerson father, SimplePerson mother, int birthYear, Animal pet) {
        this.name = name;
        Father = father;
        Mother = mother;
        this.birthYear = birthYear;
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public SimplePerson getFather() {
        return Father;
    }

    public SimplePerson getMother() {
        return Mother;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public Animal getPet() {
        return pet;
    }

    public void setName(String name) {
        this.name = name;
    }
}
