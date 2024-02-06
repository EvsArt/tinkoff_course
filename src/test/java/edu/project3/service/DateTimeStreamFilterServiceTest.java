package edu.project3.service;

import edu.project3.pojo.Arguments;
import edu.project3.pojo.LogInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeStreamFilterServiceTest {

    DateTimeStreamFilterService service = new DateTimeStreamFilterService();

    @Test
    void useTimeArgsForStream() {

        LogInfo li1 = new LogInfo().setDateTime(LocalDateTime.of(2023, 8, 31, 12, 21));
        LogInfo li2 = new LogInfo().setDateTime(LocalDateTime.of(2022, 9, 12, 12, 22));
        LogInfo li3 = new LogInfo().setDateTime(LocalDateTime.of(2024, 8, 31, 12, 21));
        Arguments argumentsWithDateConstraints = new Arguments(List.of(), "2023-08-31", null, "");

        Stream<LogInfo> logsWithDateStream = Stream.of(li1, li2, li3);
        List<LogInfo> res = service.useTimeArgsForStream(logsWithDateStream, argumentsWithDateConstraints).toList();

        List<LogInfo> expRes = List.of(li1, li3);
        assertEquals(res, expRes);

    }
}
