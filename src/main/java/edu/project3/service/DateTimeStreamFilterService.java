package edu.project3.service;

import edu.project3.pojo.Arguments;
import edu.project3.pojo.LogInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class DateTimeStreamFilterService {

    public Stream<LogInfo> useTimeArgsForStream(Stream<LogInfo> stream, Arguments args) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate from =
            LocalDate.parse((args.from() == null ? LocalDateTime.MIN.format(formatter) : args.from()), formatter);
        LocalDate to =
            LocalDate.parse(
                (args.to() == null ? LocalDateTime.MAX.minusDays(1).format(formatter) : args.to()),
                formatter
            );

        return stream
            .filter(it -> it.getDateTime().isAfter(from.atStartOfDay()))
            .filter(it -> it.getDateTime().isBefore(to.plusDays(1).atStartOfDay()));

    }

}
