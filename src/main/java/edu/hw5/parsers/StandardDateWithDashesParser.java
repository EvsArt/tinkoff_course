package edu.hw5.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class StandardDateWithDashesParser implements Parser {

    @Override
    public Optional<LocalDate> parse(String stringDate) {
        Objects.requireNonNull(stringDate);
        if (!Pattern.matches("^(\\d+)-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$", stringDate)) {
            return Optional.empty();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return Optional.of(
                LocalDate.from(
                    format.parse(stringDate)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                )
            );
        } catch (ParseException e) {
            return Optional.empty();
        }
    }
}
