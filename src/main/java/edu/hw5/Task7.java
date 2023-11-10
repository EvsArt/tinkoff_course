package edu.hw5;

import java.util.Objects;
import java.util.regex.Pattern;

public class Task7 {

    public boolean hasNotLessThan3SymbolsAnd3dSymbolIs0(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^[01]{2}0[01]*$");
        return pattern.matcher(input).matches();
    }

    public boolean isStartAndEndWithSimilarSymbols(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^(0[01]*0)|(1[01]*1)|0|1$");
        return pattern.matcher(input).matches();
    }

    public boolean hasLengthNotLess1AndNotMore3(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^[01]{1,3}$");
        return pattern.matcher(input).matches();
    }

    private void assertCorrectString(String input) {
        Objects.requireNonNull(input);

        Pattern pattern = Pattern.compile("^[01]*$");
        if (!pattern.matcher(input).matches()) {
            throw new IllegalArgumentException("Argument " + input + " is wrong!");
        }
    }

}
