package edu.hw5;

import java.util.Objects;
import java.util.regex.Pattern;

public class Task8 {

    public boolean isEvenLength(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^(..)*$");
        return pattern.matcher(input).matches();
    }

    public boolean isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^(0(..)*)|((1)[01](..)*)$");
        return pattern.matcher(input).matches();
    }

    public boolean countOfZerosIsEquals3(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^1*01*01*01*$");
        return pattern.matcher(input).find();
    }

    public boolean isNot11Or111(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^11(1?)$");
        return !pattern.matcher(input).matches();
    }

    public boolean isEveryNotEvenSymbolIs1(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("^((1(.1)*)|(1.)*)$");
        return pattern.matcher(input).matches();
    }

    public boolean containsNotLessThan2ZerosAndNotMoreThan1Ones(String input) {
        assertCorrectString(input);

        Pattern patternForZeros = Pattern.compile("0.*0.*");
        Pattern patternForOnes = Pattern.compile("1.*1.*");
        return patternForZeros.matcher(input).find() && !patternForOnes.matcher(input).find();
    }

    public boolean notHaveSerialOnes(String input) {
        assertCorrectString(input);

        Pattern pattern = Pattern.compile("11");
        return !pattern.matcher(input).find();

    }

    private void assertCorrectString(String input) {
        Objects.requireNonNull(input);

        Pattern pattern = Pattern.compile("^[01]*$");
        if (!pattern.matcher(input).matches()) {
            throw new IllegalArgumentException("Argument " + input + " is wrong!");
        }
    }

}
