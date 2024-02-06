package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Task3p5Test {

    @Test
    public void normalFunctionalityTest() throws InterruptedException {
        PersonDatabase database = new Task3p5.MapPersonDatabase();

        for (int i = 0; i < 90; i += 3) {
            Person person1 = new Person(i, "Artem", "Data", "12345");
            Person person2 = new Person(i + 1, "Artem", "Dom", "12345");
            Person person3 = new Person(i + 2, "Anna", "Dom", "12345");
            database.add(person1);
            database.add(person2);
            database.add(person3);
            Thread.sleep(50);
            database.delete(person2.id());
        }
        Thread.sleep(1000);

        int artemListSize = database.findByName("Artem").size();
        int annaListSize = database.findByName("Anna").size();
        int phone12345ListSize = database.findByPhone("12345").size();

        assertEquals(artemListSize, 30);
        assertEquals(annaListSize, 30);
        assertEquals(phone12345ListSize, 60);
    }

    @Test
    public void findXDoesNotHaveNullFields() throws InterruptedException {
        PersonDatabase database = new Task3p5.MapPersonDatabase();

        Set<List<Person>> findingPersonsLists = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            String myName = "myName";
            String myAddress = "Address" + i;
            String myPhone = "8800" + i;
            Person person = new Person(i, myName, myAddress, myPhone);
            database.add(person);
            findingPersonsLists.add(database.findByName(myName));
            findingPersonsLists.add(database.findByAddress(myAddress));
            findingPersonsLists.add(database.findByPhone(myPhone));
        }
        Thread.sleep(1000);

        boolean hasPersonsWithNulls = findingPersonsLists.stream()
            .filter(Objects::nonNull)
            .anyMatch(list -> list.stream()
                .anyMatch(person -> person.address() == null || person.name() == null || person.phoneNumber() == null)
            );

        assertFalse(hasPersonsWithNulls);

    }

}
