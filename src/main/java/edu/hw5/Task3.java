package edu.hw5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class Task3 {

    public Optional<LocalDate> parseDate(String string) {
        List<Parser> parsersChain = getChainOfParsers();

        return parsersChain
            .stream()
            .map(it -> it.parse(string))
            .filter(Optional::isPresent)
            .findFirst()
            .orElse(Optional.empty());
    }

    private List<Parser> getChainOfParsers() {

        return List.of(
            new StandardDateWithDashesParser(),
                new StandardDateWithSlashesAndFullYearParser(),
                new StandardDateWithSlashesAndShortenYearParser(),
                new NearDaysParser(),
                new NDaysAgoParser()
        );

    }

    interface Parser {
        Optional<LocalDate> parse(String stringDate);
    }

    static class StandardDateWithDashesParser implements Parser {

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

    static class StandardDateWithSlashesAndFullYearParser implements Parser {

        @Override
        public Optional<LocalDate> parse(String stringDate) {
            Objects.requireNonNull(stringDate);
            if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(\\d{4})$", stringDate)) {
                return Optional.empty();
            }

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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

    static class StandardDateWithSlashesAndShortenYearParser implements Parser {

        @Override
        public Optional<LocalDate> parse(String stringDate) {
            Objects.requireNonNull(stringDate);
            if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(\\d{2})$", stringDate)) {
                return Optional.empty();
            }

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
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

    static class NearDaysParser implements Parser {

        @Override
        public Optional<LocalDate> parse(String stringDate) {
            Objects.requireNonNull(stringDate);
            if (!Pattern.matches("^yesterday|today|tomorrow$", stringDate.toLowerCase())) {
                return Optional.empty();
            }

            LocalDate now = LocalDate.now();
            return switch (stringDate.toLowerCase()) {
                case "yesterday" -> Optional.of(now.minusDays(1));
                case "today" -> Optional.of(now);
                case "tomorrow" -> Optional.of(now.plusDays(1));
                default -> throw new IllegalStateException("Unexpected value: " + stringDate.toLowerCase());
            };
        }

    }

    static class NDaysAgoParser implements Parser {

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
}


