package edu.hw10.task1.testModel;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Child {

    private final int id;
    private final String name;
    private final int age;

    public Child(int id, String name, @Min("0") @Max("18") int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
