package edu.hw10.task1.testModel;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Person {

    private final Integer id;
    private final String name;
    private final int age;
    private final Child child;

    public Person(
        @NotNull Integer id,
        String name,
        @Min("19") @Max("100") int age,
        @NotNull Child child
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.child = child;
    }

    public static Person create(
        @NotNull Integer id,
        String name,
        @Min("19") @Max("100") int age,
        @NotNull Child child
    ) {
        return new Person(id, name, age, child);
    }

}
