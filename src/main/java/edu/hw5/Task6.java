package edu.hw5;

import java.util.Objects;
import java.util.regex.Pattern;

public class Task6 {

    public boolean checkSIsSubstringT(String s, String t) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(t);

        Pattern pattern = Pattern.compile(s);
        return pattern.matcher(t).find();

    }

}
