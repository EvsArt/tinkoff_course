package edu.project3.service;

import edu.project3.pojo.LogInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {

    LogParser parser = new LogParser();

    static Path logsPath;

    @BeforeAll
    static void createLogFiles() {
        SourceService service = new SourceService();
        logsPath = service.getLogFilesBySources(List.of(
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs"
        )).getFirst();
    }

    @Test
    void getLogsStream() throws IOException {
        Stream<LogInfo> stream = parser.getLogsStream(List.of(logsPath));

        assertThat(stream.count()).isPositive();

    }

}
