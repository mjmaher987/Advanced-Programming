package model.zoo;

public class Cat implements Animal{
    private int age;
    private String name;

    public Cat() {
    }

    public Cat(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public void Sleep() { }

    @Override
    public void eat(String food) {}

    @Override
    public String makeSound() {
        return "Meow, Meow";
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
