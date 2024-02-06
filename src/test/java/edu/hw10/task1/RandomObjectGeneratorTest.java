package edu.hw10.task1;

import edu.hw10.task1.testModel.CoordinateRecord;
import edu.hw10.task1.testModel.Person;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomObjectGeneratorTest {

    @Test
    void nextObjectByConstructor() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();
        // when
        Person person = rog.nextObject(Person.class);
        // then
        assertThat(person.getId()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertNotNull(person.getName());
        assertThat(person.getAge()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertNotNull(person.getChild().getName());
    }

    @Test
    void nextRecordByConstructor() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();
        // when
        CoordinateRecord person = rog.nextObject(CoordinateRecord.class);
        // then
        assertThat(person.x()).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
        assertThat(person.y()).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    void nextObjectByFabricMethod() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();
        // when
        Person person = rog.nextObject(Person.class, "create");
        // then
        assertThat(person.getId()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertNotNull(person.getName());
        assertThat(person.getAge()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertNotNull(person.getChild().getName());
    }

    @Test
    void testAnnotationWorking() {
        // given
        RandomObjectGenerator rog = new RandomObjectGenerator();
        // when
        Person person = rog.nextObject(Person.class);
        // then
        assertThat(person.getAge()).isBetween(19, 100);
        assertThat(person.getChild().getAge()).isBetween(0, 18);
    }

}
