package edu.hw5;

import edu.hw5.parsers.NDaysAgoParser;
import edu.hw5.parsers.NearDaysParser;
import edu.hw5.parsers.Parser;
import edu.hw5.parsers.StandardDateWithDashesParser;
import edu.hw5.parsers.StandardDateWithSlashesAndFullYearParser;
import edu.hw5.parsers.StandardDateWithSlashesAndShortenYearParser;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
}


