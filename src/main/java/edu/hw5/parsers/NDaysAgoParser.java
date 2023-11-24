package edu.hw5.parsers;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class NDaysAgoParser implements Parser {

    @Override
    public Optional<LocalDate> parse(String stringDate) {
        Objects.requireNonNull(stringDate);
        if (!Pattern.matches("^\\d+ days? ago$", stringDate.toLowerCase())) {
            return Optional.empty();
        }

        int daysAgo = Integer.parseInt(stringDate.split(" ")[0]);
        return Optional.of(LocalDate.now().minusDays(daysAgo));

    }

}
