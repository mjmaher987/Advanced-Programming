import model.man.Person;
import model.man.ShowOff;
import model.man.SimplePerson;
import model.man.Son;
import model.primitive.ArrayContainer;
import model.primitive.Circle;
import model.primitive.Stationery;
import model.zoo.Cat;
import model.zoo.Lamb;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static model.primitive.Stationery.Type.PEN;
import static model.primitive.Stationery.Type.PENCIL;
import static org.junit.Assert.*;

public class Judge {
    private static Agent agent;
    private Person person;

    @BeforeClass
    public static void beforeAll() {
        agent = new Agent();
    }

    @Before
    public void setup() {
        person = new Person(LocalDate.of(2001, Month.JANUARY, 17), "Ali");
    }

    private void assertThatListsAreEqualIgnoreOrder (List<String> first, List<String> second) {
        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
    }

    @Test
    public void should_ReturnAllMethods_When_AllMethodsAreAccessible(){
        ShowOff showOff = new ShowOff();
        List<String> agentMethodNames = agent.getMethodNames(showOff);
        List<String> expectedMethodNames = Arrays.asList("sayHaveNoProblems",
                "pretendHaveAGreatLife",
                "askForAttention");
        assertThatListsAreEqualIgnoreOrder(agentMethodNames, expectedMethodNames);
    }

    @Test
    public void should_ReturnAllMethods_Whenever() {
        List<String> agentMethodNames = agent.getMethodNames(person);
        List<String> expectedMethodNames = Arrays.asList("setPopulation",
                "getAge",
                "getBirthdate",
                "isNameEquals",
                "getPopulation",
                "foo");
        assertThatListsAreEqualIgnoreOrder(agentMethodNames, expectedMethodNames);
    }

    @Test
    public void should_ReturnContent_When_FieldIsAccessible() throws Exception {
        Object agentNameReturn = agent.getFieldContent(person, "name");
        String expectedName = "Ali";
        assertEquals(expectedName, agentNameReturn);
    }

    @Test
    public void should_ReturnFiledContent_When_FieldIsNotAccessible() throws Exception {
        Object agentBirthdateReturn = agent.getFieldContent(person, "birthdate");
        LocalDate expectedBirthdate = LocalDate.of(2001, Month.JANUARY, 17);
        assertEquals(expectedBirthdate, agentBirthdateReturn);
    }


    @Test (expected = NoSuchFieldException.class)
    public void should_getFiledContent_ThrowNoSuchField_When_FieldNameIsInvalid() throws Exception {
        agent.getFieldContent(person, "nationalId");
    }

    @Test
    public void should_SetFieldContent_When_FieldIsAccessible() throws Exception {
        agent.setFieldContent(person, "name", "Maryam");
        assertEquals("Maryam", person.name);
    }

    @Test
    public void should_SetFiledContent_When_FieldIsNotAccessible() throws Exception {
        agent.setFieldContent(person, "population", 68);
        assertEquals(68, Person.getPopulation());
    }

    @Test
    public void should_NotSetFieldContent_When_ContentsDoNotMatch() throws Exception {
        agent.setFieldContent(person, "birthdate", 987456321);
        LocalDate actualBD = person.getBirthdate();
        LocalDate expectedBD = LocalDate.of(2001, Month.JANUARY, 17);
        assertEquals(expectedBD, actualBD);
    }

    @Test (expected = NoSuchFieldException.class)
    public void should_setFiledContent_ThrowNoSuchField_When_FieldNameIsInvalid() throws Exception {
        agent.setFieldContent(person, "nationalId", null);
    }

    @Test
    public void should_setFieldContent_When_FieldIsStatic() throws Exception{
        Son son = getNewSonInstance();
        agent.setFieldContent(son, "INT", 8);
        assertEquals(8, Son.getINT());
    }

    @Test
    public void should_setFiledContent_When_FiledIsFinal() throws Exception {
        Son son = getNewSonInstance();
        agent.setFieldContent(son, "stationery", new Stationery(5, PENCIL));
        assertEquals(5, son.getStationery().price);
    }

    @Test
    public void should_CallRightMethod_When_InputsAreValid() throws Exception {
        Object actualReturnValue = agent.call(person, "isNameEquals", new Object[]{"Ali"});
        assertEquals(true, actualReturnValue);
    }

    @Test
    public void should_InvokeMethods_When_EmptyParameters() throws Exception {
        {
            Object actualGetAgeReturn = agent.call(person, "getAge", new Object[0]);
            assertEquals(20, actualGetAgeReturn);
        }
        {
            Object actualGetBirthdateReturn = agent.call(person, "getBirthdate", new Object[0]);
            LocalDate expectedBirthdate = LocalDate.of(2001, Month.JANUARY, 17);
            assertEquals(expectedBirthdate, actualGetBirthdateReturn);
        }
    }

    @Test (expected = NoSuchMethodException.class)
    public void should_ThrowNoSuchMethod_When_InvalidName() throws Exception {
        agent.call(person, "getHeight", new Object[0]);
    }

    @Test (expected = NoSuchMethodException.class)
    public void should_ThrowNoSuchMethod_When_ParametersAreIncompatible() throws Exception {
        agent.call(person, "getBirthdate", new Object[]{LocalDate.now()});
    }

    @Test
    public void should_CreateANewInstance_When_InputsAreValid() throws Exception {
        Object newObject = agent.createANewObject("model.man.Person",
                new Object[]{LocalDate.of(2022,Month.AUGUST,26),"Amir Hossein"});
        assertTrue(newObject instanceof Person);
        assertEquals("Amir Hossein", ((Person) newObject).name);
        assertEquals(LocalDate.of(2022,Month.AUGUST,26),((Person) newObject).getBirthdate());
    }

    @Test (expected = ClassNotFoundException.class)
    public void should_ThrowClassNotFound_When_ClassNameIsInvalid() throws Exception {
        agent.createANewObject("man.Person", new Object[0]);
    }

    @Test (expected = NoSuchMethodException.class)
    public void should_ThrowNoSuchMethod_When_InitialsAreIncompatible() throws Exception {
        agent.createANewObject("model.man.Person", new Object[]{LocalDate.now()});
    }

    @Test
    public void should_ReturnCompleteReport_When_SomeConstructorsAreInaccessible() {
        Circle circle = new Circle(5);
        Object actualReport = agent.debrief(circle);
        String expectedReport = getCircleReport();
        assertEquals(expectedReport, actualReport);
    }

    private String getCircleReport() {
        return  "Name: Circle\n" +
                "Package: model.primitive\n" +
                "No. of Constructors: 2\n" +
                "===\n" +
                "Fields:\n" +
                "private float radius\n" +
                "(1 fields)\n" +
                "===\n" +
                "Methods:\n" +
                "double getPerimeter()\n" +
                "float getRadius()\n" +
                "double getSurface()\n" +
                "void setRadius(float)\n" +
                "(4 methods)";
    }

    @Test
    public void should_ReturnCompleteReport_When_ThereAreInaccessibleItems() {
        Stationery stationery = new Stationery(25, PENCIL);
        Object actualReport = agent.debrief(stationery);
        String expectedReport = getStationaryReport();
        assertEquals(expectedReport, actualReport);
    }

    private String getStationaryReport() {
        return  "Name: Stationery\n" +
                "Package: model.primitive\n" +
                "No. of Constructors: 2\n" +
                "===\n" +
                "Fields:\n" +
                "public int price\n" +
                "private Type type\n"+
                "(2 fields)\n" +
                "===\n" +
                "Methods:\n" +
                "int getPrice()\n" +
                "Type getType()\n" +
                "void setPrice(int)\n" +
                "void setType(Type)\n" +
                "(4 methods)";
    }

    @Test
    public void should_ReturnCompleteReport_When_ModifiersAreComplicated() {
        Object actualReport = agent.debrief(person);
        String expectedReport = getPersonReport();
        assertEquals(expectedReport, actualReport);
    }

    private String getPersonReport() {
        return "Name: Person\n" +
                "Package: model.man\n" +
                "No. of Constructors: 3\n" +
                "===\n" +
                "Fields:\n" +
                "protected LocalDate birthdate\n" +
                "public String name\n" +
                "protected static int population\n" +
                "(3 fields)\n" +
                "===\n" +
                "Methods:\n" +
                "void foo(String, Object, Integer, int, List)\n" +
                "int getAge()\n" +
                "LocalDate getBirthdate()\n" +
                "int getPopulation()\n" +
                "boolean isNameEquals(String)\n" +
                "void setPopulation(Integer)\n" +
                "(6 methods)";
    }

    @Test
    public void should_Clone_When_ClassIsSimple() throws Exception{
        Circle circle = new Circle(6);
        Object clone = agent.clone(circle);
        assertTrue(clone instanceof Circle);
        assertTrue(6.0 == ((Circle) clone).getRadius());
        ((Circle) clone).setRadius(87);
        assertNotEquals(circle.getRadius(), ((Circle) clone).getRadius());
    }

    @Test
    public void should_Clone_When_ClassHasComplicatedFields() throws Exception {
        SimplePerson beth = getNewMotherInstance();
        Object clone = agent.clone(beth);
        assertTrue(clone instanceof SimplePerson);
        SimplePerson bethClone = (SimplePerson) clone;
        assertNotNull(((SimplePerson) clone).getPet());
        assertEquals("Baaaaaaaaaaa", bethClone.getPet().makeSound());
    }

    private SimplePerson getNewMotherInstance() {
        return getNewSonInstance().getMother();
    }

    private Son getNewSonInstance() {
        SimplePerson grandfather = new SimplePerson("Rick", null, null, 1960,null);
        SimplePerson grandmother = new SimplePerson("Diane", null, null, 1963,null);
        SimplePerson mother = new SimplePerson("Beth", grandfather, grandmother, 1983,
                new Lamb(5, "Babae"));
        SimplePerson father = new SimplePerson("Jerry", null, null, 1979, null);

        return new Son("Morty", father, mother, 2004, new Cat(2, "Gavin"), 164);
    }

    @Test
    public void should_Clone_When_FieldIsEnum() throws Exception {
        Stationery stationery = new Stationery(25, PENCIL);
        Stationery clone = (Stationery) agent.clone(stationery);
        clone.setType(PEN);
    }

    @Test
    public void should_Clone_When_FieldIsArray() throws Exception {
        ArrayContainer container = new ArrayContainer();
        ArrayContainer clone = (ArrayContainer) agent.clone(container);
        clone.getInts()[0] = 25;
        clone.getBytes()[0] = 1;
        clone.getChars()[0] = 'm';

        assertNotEquals(container.getInts()[0], clone.getInts()[0]);
        assertNotEquals(container.getBytes()[0], clone.getBytes()[0]);
        assertNotEquals(container.getChars()[0], clone.getChars()[0]);
    }

    @Test
    public void should_Clone_When_DeepCopyIsRequired() throws Exception {
        Son morty = getNewSonInstance();
        Object clone = agent.clone(morty);
        assertTrue(clone instanceof Son);
        Son cloneMorty = (Son) clone;

        assertNotNull(cloneMorty.getMother().getPet());

        cloneMorty.setName("NotMorty");
        cloneMorty.getPet().setName("Lucifer");
        cloneMorty.getMother().setName("Jessica");
        cloneMorty.getMother().getPet().setName("Vanilla");
        cloneMorty.getMother().getFather().setName("Rick C-137");

        assertNotEquals(morty.getName(), cloneMorty.getName());
        assertNotEquals(morty.getPet().getName(), cloneMorty.getPet().getName());
        assertNotEquals(morty.getMother().getName(), cloneMorty.getMother().getName());
        assertNotEquals(morty.getMother().getPet().getName(), cloneMorty.getMother().getPet().getName());
        assertNotEquals(morty.getMother().getFather().getName(), cloneMorty.getMother().getFather().getName());
    }
}



//import model.man.Person;
//import model.man.ShowOff;
//import model.man.SimplePerson;
//import model.man.Son;
//import model.primitive.ArrayContainer;
//import model.primitive.Circle;
//import model.primitive.Stationery;
//import model.zoo.Cat;
//import model.zoo.Lamb;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.Arrays;
//import java.util.List;
//
//import static model.primitive.Stationery.Type.PEN;
//import static model.primitive.Stationery.Type.PENCIL;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class Judge {
//    private static Agent agent;
//    private Person person;
//
//    @BeforeAll
//    public static void beforeAll() {
//        agent = new Agent();
//    }
//
//    @BeforeEach
//    public void setup() {
//        person = new Person(LocalDate.of(2001, Month.JANUARY, 17), "Ali");
//    }
//
//    private void assertThatListsAreEqualIgnoreOrder(List<String> first, List<String> second) {
//        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
//    }
//
//    @Test
//    public void should_ReturnAllMethods_When_AllMethodsAreAccessible() {
//        ShowOff showOff = new ShowOff();
//        List<String> agentMethodNames = agent.getMethodNames(showOff);
//        List<String> expectedMethodNames = Arrays.asList("sayHaveNoProblems",
//                "pretendHaveAGreatLife",
//                "askForAttention");
//        assertThatListsAreEqualIgnoreOrder(agentMethodNames, expectedMethodNames);
//    }
//
//    @Test
//    public void should_ReturnAllMethods_Whenever() {
//        List<String> agentMethodNames = agent.getMethodNames(person);
//        List<String> expectedMethodNames = Arrays.asList("setPopulation",
//                "getAge",
//                "getBirthdate",
//                "isNameEquals",
//                "getPopulation",
//                "foo");
//        assertThatListsAreEqualIgnoreOrder(agentMethodNames, expectedMethodNames);
//    }
//
//    @Test
//    public void should_ReturnContent_When_FieldIsAccessible() {
//        assertDoesNotThrow(() -> {
//            Object agentNameReturn = agent.getFieldContent(person, "name");
//            String expectedName = "Ali";
//            assertEquals(expectedName, agentNameReturn);
//        });
//    }
//
//    @Test
//    public void should_ReturnFiledContent_When_FieldIsNotAccessible() {
//        assertDoesNotThrow(() -> {
//            Object agentBirthdateReturn = agent.getFieldContent(person, "birthdate");
//            LocalDate expectedBirthdate = LocalDate.of(2001, Month.JANUARY, 17);
//            assertEquals(expectedBirthdate, agentBirthdateReturn);
//        });
//    }
//
//    @Test
//    public void should_getFiledContent_ThrowNoSuchField_When_FieldNameIsInvalid() {
//        assertThrows(NoSuchFieldException.class,
//                () -> agent.getFieldContent(person, "nationalId"));
//    }
//
//    @Test
//    public void should_SetFieldContent_When_FieldIsAccessible() {
//        assertDoesNotThrow(() -> {
//            agent.setFieldContent(person, "name", "Maryam");
//            assertEquals("Maryam", person.name);
//        });
//    }
//
//    @Test
//    public void should_SetFiledContent_When_FieldIsNotAccessible() {
//        assertDoesNotThrow(() -> {
//            agent.setFieldContent(person, "population", 68);
//            assertEquals(68, Person.getPopulation());
//        });
//    }
//
//    @Test
//    public void should_NotSetFieldContent_When_ContentsDoNotMatch() {
//        assertDoesNotThrow(() -> {
//            agent.setFieldContent(person, "birthdate", 987456321);
//            LocalDate actualBD = person.getBirthdate();
//            LocalDate expectedBD = LocalDate.of(2001, Month.JANUARY, 17);
//            assertEquals(expectedBD, actualBD);
//        });
//    }
//
//    @Test
//    public void should_setFiledContent_ThrowNoSuchField_When_FieldNameIsInvalid() {
//        assertThrows(NoSuchFieldException.class,
//                () -> agent.setFieldContent(person, "nationalId", null));
//    }
//
//    @Test
//    public void should_setFieldContent_When_FieldIsStatic() {
//        assertDoesNotThrow(() -> {
//            Son son = getNewSonInstance();
//            agent.setFieldContent(son, "INT", 8);
//            assertEquals(8, Son.getINT());
//        });
//    }
//
//    @Test
//    public void should_setFiledContent_When_FiledIsFinal() {
//        assertDoesNotThrow(() -> {
//            Son son = getNewSonInstance();
//            agent.setFieldContent(son, "stationery", new Stationery(5, PENCIL));
//            assertEquals(5, son.getStationery().price);
//        });
//    }
//
//    @Test
//    public void should_CallRightMethod_When_InputsAreValid() {
//        assertDoesNotThrow(() -> {
//            Object actualReturnValue = agent.call(person, "isNameEquals", new Object[]{"Ali"});
//            assertEquals(true, actualReturnValue);
//        });
//    }
//
//    @Test
//    public void should_InvokeMethods_When_EmptyParameters() {
//        assertAll(
//            () -> assertDoesNotThrow(() -> {
//                Object actualGetAgeReturn = agent.call(person, "getAge", new Object[0]);
//                assertEquals(20, actualGetAgeReturn);
//            }),
//            () -> assertDoesNotThrow(() -> {
//                Object actualGetBirthdateReturn = agent.call(person, "getBirthdate", new Object[0]);
//                LocalDate expectedBirthdate = LocalDate.of(2001, Month.JANUARY, 17);
//                assertEquals(expectedBirthdate, actualGetBirthdateReturn);
//            })
//        );
//    }
//
//    @Test
//    public void should_ThrowNoSuchMethod_When_InvalidName() {
//        assertThrows(NoSuchMethodException.class,
//                () -> agent.call(person, "getHeight", new Object[0]));
//    }
//
//    @Test
//    public void should_ThrowNoSuchMethod_When_ParametersAreIncompatible() {
//        assertThrows(NoSuchMethodException.class,
//                () -> agent.call(person, "getBirthdate", new Object[]{LocalDate.now()}));
//    }
//
//    @Test
//    public void should_CreateANewInstance_When_InputsAreValid() {
//        assertDoesNotThrow(() -> {
//            Object newObject = agent.createANewObject("model.man.Person",
//                    new Object[]{LocalDate.of(2022, Month.AUGUST, 26), "Amir Hossein"});
//            assertTrue(newObject instanceof Person);
//            assertAll(
//                () -> assertEquals("Amir Hossein", ((Person) newObject).name),
//                () -> assertEquals(LocalDate.of(2022, Month.AUGUST, 26), ((Person) newObject).getBirthdate())
//            );
//        });
//    }
//
//    @Test
//    public void should_ThrowClassNotFound_When_ClassNameIsInvalid() {
//        assertThrows(ClassNotFoundException.class,
//                () -> agent.createANewObject("man.Person", new Object[0]));
//    }
//
//    @Test
//    public void should_ThrowNoSuchMethod_When_InitialsAreIncompatible() {
//        assertThrows(NoSuchMethodException.class,
//                () -> agent.createANewObject("model.man.Person", new Object[]{LocalDate.now()}));
//    }
//
//    @Test
//    public void should_ReturnCompleteReport_When_SomeConstructorsAreInaccessible() {
//        Circle circle = new Circle(5);
//        Object actualReport = agent.debrief(circle);
//        String expectedReport = getCircleReport();
//        assertEquals(expectedReport, actualReport);
//    }
//
//    private String getCircleReport() {
//        return "Name: Circle\n" +
//                "Package: model.primitive\n" +
//                "No. of Constructors: 2\n" +
//                "===\n" +
//                "Fields:\n" +
//                "private float radius\n" +
//                "(1 fields)\n" +
//                "===\n" +
//                "Methods:\n" +
//                "double getPerimeter()\n" +
//                "float getRadius()\n" +
//                "double getSurface()\n" +
//                "void setRadius(float)\n" +
//                "(4 methods)";
//    }
//
//    @Test
//    public void should_ReturnCompleteReport_When_ThereAreInaccessibleItems() {
//        Stationery stationery = new Stationery(25, PENCIL);
//        Object actualReport = agent.debrief(stationery);
//        String expectedReport = getStationaryReport();
//        assertEquals(expectedReport, actualReport);
//    }
//
//    private String getStationaryReport() {
//        return "Name: Stationery\n" +
//                "Package: model.primitive\n" +
//                "No. of Constructors: 2\n" +
//                "===\n" +
//                "Fields:\n" +
//                "public int price\n" +
//                "private Type type\n" +
//                "(2 fields)\n" +
//                "===\n" +
//                "Methods:\n" +
//                "int getPrice()\n" +
//                "Type getType()\n" +
//                "void setPrice(int)\n" +
//                "void setType(Type)\n" +
//                "(4 methods)";
//    }
//
//    @Test
//    public void should_ReturnCompleteReport_When_ModifiersAreComplicated() {
//        Object actualReport = agent.debrief(person);
//        String expectedReport = getPersonReport();
//        assertEquals(expectedReport, actualReport);
//    }
//
//    private String getPersonReport() {
//        return "Name: Person\n" +
//                "Package: model.man\n" +
//                "No. of Constructors: 3\n" +
//                "===\n" +
//                "Fields:\n" +
//                "protected LocalDate birthdate\n" +
//                "public String name\n" +
//                "protected static int population\n" +
//                "(3 fields)\n" +
//                "===\n" +
//                "Methods:\n" +
//                "void foo(String, Object, Integer, int, List)\n" +
//                "int getAge()\n" +
//                "LocalDate getBirthdate()\n" +
//                "int getPopulation()\n" +
//                "boolean isNameEquals(String)\n" +
//                "void setPopulation(Integer)\n" +
//                "(6 methods)";
//    }
//
//    @Test
//    public void should_Clone_When_ClassIsSimple() {
//        assertDoesNotThrow(() -> {
//            Circle circle = new Circle(6);
//            Object clone = agent.clone(circle);
//            assertTrue(clone instanceof Circle);
//            assertEquals(6.0, ((Circle) clone).getRadius());
//            ((Circle) clone).setRadius(87);
//            assertNotEquals(circle.getRadius(), ((Circle) clone).getRadius());
//        });
//    }
//
//    @Test
//    public void should_Clone_When_ClassHasComplicatedFields() {
//        assertDoesNotThrow(() -> {
//            SimplePerson beth = getNewMotherInstance();
//            Object clone = agent.clone(beth);
//            assertTrue(clone instanceof SimplePerson);
//            SimplePerson bethClone = (SimplePerson) clone;
//            assertNotNull(((SimplePerson) clone).getPet());
//            assertEquals("Baaaaaaaaaaa", bethClone.getPet().makeSound());
//        });
//    }
//
//    private SimplePerson getNewMotherInstance() {
//        return getNewSonInstance().getMother();
//    }
//
//    private Son getNewSonInstance() {
//        SimplePerson grandfather = new SimplePerson("Rick", null, null, 1960, null);
//        SimplePerson grandmother = new SimplePerson("Diane", null, null, 1963, null);
//        SimplePerson mother = new SimplePerson("Beth", grandfather, grandmother, 1983,
//                new Lamb(5, "Babae"));
//        SimplePerson father = new SimplePerson("Jerry", null, null, 1979, null);
//
//        return new Son("Morty", father, mother, 2004, new Cat(2, "Gavin"), 164);
//    }
//
//    @Test
//    public void should_Clone_When_FieldIsEnum() {
//        assertDoesNotThrow(() -> {
//            Stationery stationery = new Stationery(25, PENCIL);
//            Stationery clone = (Stationery) agent.clone(stationery);
//            clone.setType(PEN);
//        });
//    }
//
//    @Test
//    public void should_Clone_When_FieldIsArray() {
//        assertDoesNotThrow(() -> {
//            ArrayContainer container = new ArrayContainer();
//            ArrayContainer clone = (ArrayContainer) agent.clone(container);
//            clone.getInts()[0] = 25;
//            clone.getBytes()[0] = 1;
//            clone.getChars()[0] = 'm';
//
//            assertNotEquals(container.getInts()[0], clone.getInts()[0]);
//            assertNotEquals(container.getBytes()[0], clone.getBytes()[0]);
//            assertNotEquals(container.getChars()[0], clone.getChars()[0]);
//        });
//    }
//
//    @Test
//    public void should_Clone_When_DeepCopyIsRequired() {
//        assertDoesNotThrow(() -> {
//            Son morty = getNewSonInstance();
//            Object clone = agent.clone(morty);
//            assertTrue(clone instanceof Son);
//            Son cloneMorty = (Son) clone;
//
//            assertNotNull(cloneMorty.getMother().getPet());
//
//            cloneMorty.setName("NotMorty");
//            cloneMorty.getPet().setName("Lucifer");
//            cloneMorty.getMother().setName("Jessica");
//            cloneMorty.getMother().getPet().setName("Vanilla");
//            cloneMorty.getMother().getFather().setName("Rick C-137");
//
//            assertAll(
//                () -> assertNotEquals(morty.getName(), cloneMorty.getName()),
//                () -> assertNotEquals(morty.getPet().getName(), cloneMorty.getPet().getName()),
//                () -> assertNotEquals(morty.getMother().getName(), cloneMorty.getMother().getName()),
//                () -> assertNotEquals(morty.getMother().getPet().getName(), cloneMorty.getMother().getPet().getName()),
//                () -> assertNotEquals(
//                        morty.getMother().getFather().getName(),
//                        cloneMorty.getMother().getFather().getName()
//                )
//            );
//        });
//    }
//}