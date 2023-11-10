package edu.hw5;

import java.util.Objects;
import java.util.regex.Pattern;

public class Task5 {

    public boolean validCarLicensePlateNumber(String number) {
        Objects.requireNonNull(number);

        String allowedLetters = "АВЕКМНОРСТУХ";
        Pattern pattern = Pattern.compile(
            String.format("^[%s]\\d{3}[%s]{2}\\d{2,3}$", allowedLetters, allowedLetters)
        );
        return pattern.matcher(number).matches();
    }

}
