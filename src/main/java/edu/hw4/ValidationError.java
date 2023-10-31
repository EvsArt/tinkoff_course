package edu.hw4;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum ValidationError {

    NAMEISNULLERROR(
        "Name is null!",
        animal -> animal.name() == null || animal.name().isBlank()
    ),
    WRONGNAMEFORMATERROR(
        "The name have to be written in English letters!",
        animal -> !Pattern.matches("[a-z|A-Z]*", animal.name())
    ),
    FIRSTLETTERISNOTCAPITALERROR(
        "First letter of the name have to be capital!",
        animal -> !Character.isUpperCase(animal.name().charAt(0))
    ),
    WRONGNAMELENGTHERROR(
        "Length of the name have to be more than 1!",
        animal -> animal.name().length() <= 1
    ),
    WRONGAGEERROR(
        "Age shouldn't be < 0",
        animal -> animal.age() < 0
    ),
    WRONGHEIGHTERROR(
        "Height shouldn't be < 0",
        animal -> animal.height() < 0
    ),
    WRONGWEIGHTERROR(
        "Weight shouldn't be < 0",
        animal -> animal.weight() < 0
    );

    private final String msg;
    private final Function<Animal, Boolean> isError;

    ValidationError(String msg, Function<Animal, Boolean> isError) {
        this.msg = msg;
        this.isError = isError;
    }

    public String getMessage(){
        return msg;
    }

    public static Stream<ValidationError> getErrors(Animal animal) {

        Stream.Builder<ValidationError> builder = Stream.builder();

        if (NAMEISNULLERROR.isError.apply(animal)) {
            return builder.add(NAMEISNULLERROR).build();
        }

        Arrays
            .stream(ValidationError.values())
            .filter(it -> it.isError.apply(animal))
            .forEach(builder);

        return builder.build();
    }

}
