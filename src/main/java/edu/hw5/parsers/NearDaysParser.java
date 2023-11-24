package edu.hw5.parsers;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class NearDaysParser implements Parser {

    @Override
    public Optional<LocalDate> parse(String stringDate) {
        Objects.requireNonNull(stringDate);
        if (!Pattern.matches("^yesterday|today|tomorrow$", stringDate.toLowerCase())) {
            return Optional.empty();
        }

        LocalDate now = LocalDate.now();
        return switch (NearDays.getDayByString(stringDate)) {
            case YESTERDAY -> Optional.of(now.minusDays(1));
            case TODAY -> Optional.of(now);
            case TOMORROW -> Optional.of(now.plusDays(1));
            case null -> throw new IllegalStateException("Unexpected value: " + stringDate.toLowerCase());
        };
    }

    public enum NearDays {
        YESTERDAY("yesterday"),
        TODAY("today"),
        TOMORROW("tomorrow");

        private final String name;

        NearDays(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        static NearDays getDayByString(String s) {
            for (NearDays day : values()) {
                if (day.name.equals(s.toLowerCase())) {
                    return day;
                }
            }
            return null;
        }

    }

}
