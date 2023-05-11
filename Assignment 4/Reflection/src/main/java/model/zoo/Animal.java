package model.zoo;

public interface Animal {
    void Sleep();
    void eat(String food);
    void setName(String name);
    String getName();
    String makeSound();
}
