package model.man;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Person {
    protected static int population;

    protected LocalDate birthdate;
    public String name;

    public Person() {}

    private Person(String name) {
        this.name = name;
    }

    public Person(LocalDate birthdate, String name) {
        this.birthdate = birthdate;
        this.name = name;
    }

    private static void setPopulation(Integer population) {
        Person.population = population;
    }

    public static int getPopulation() {
        return population;
    }

    private static void foo(String _s, Object _object, Integer _integer, int _num, List<String> _strings) {}

    protected int getAge () {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    private boolean isNameEquals(String guess) {
        return this.name.equals(guess);
    }


}
