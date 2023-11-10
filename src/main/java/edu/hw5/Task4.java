package edu.hw5;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {

    public boolean checkPassword(String password) {
        Objects.requireNonNull(password);

        Pattern pattern = Pattern.compile("[~!@#$%^&*|]");
        Matcher matcher = pattern.matcher(password);

        return matcher.find();

    }

}
