package edu.project3.service;

import edu.project3.pojo.Arguments;
import edu.project3.pojo.LogInfo;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeStreamFilterServiceTest {

    DateTimeStreamFilterService service = new DateTimeStreamFilterService();

    @Test
    void useTimeArgsForStream() throws UnknownHostException {

        Arguments arguments = new Arguments(List.of(), "2023-08-31", null, "");
        Stream<LogInfo> stream = Stream.of(
            new LogInfo().setDateTime(LocalDateTime.of(2023, 8, 31, 12, 21)),
            new LogInfo().setDateTime(LocalDateTime.of(2022, 9, 12, 12, 22)),
            new LogInfo().setDateTime(LocalDateTime.of(2024, 8, 31, 12, 21))
        );
        Stream<LogInfo> res = service.useTimeArgsForStream(stream, arguments);
        assertThat(res.count()).isEqualTo(2);

    }
}
