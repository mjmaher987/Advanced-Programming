package model.zoo;

public class Lamb implements Animal {
    private int age;
    private String name;

    public Lamb() {
    }

    public Lamb(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public void Sleep() {

    }

    @Override
    public void eat(String food) {

    }

    @Override
    public String makeSound() {
        return "Baaaaaaaaaaa";
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
