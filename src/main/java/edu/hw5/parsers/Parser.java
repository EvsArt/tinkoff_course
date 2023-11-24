package edu.hw5.parsers;

import java.time.LocalDate;
import java.util.Optional;

public interface Parser {
    Optional<LocalDate> parse(String stringDate);
}
