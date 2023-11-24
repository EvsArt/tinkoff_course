package edu.project3.service;

import edu.project3.pojo.LogInfo;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LogParserTest {

    private static final Path PARENT_PATH = Path.of(".", "src", "main", "resources", "project3");
    private static final Path LOGS_PATH = PARENT_PATH.resolve("logs");
    private static final String LOGS_SOURCE =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    LogParser parser = new LogParser();

    private static List<Path> logsPath = List.of();

    @BeforeAll
    static void createLogFiles() throws IOException, InterruptedException {
        removeFiles();

        Path tmpLogFile = Files.createFile(LOGS_PATH.resolve("tmpFile.log"));

        HttpRequest request = HttpRequest.newBuilder(URI.create(LOGS_SOURCE))
            .GET()
            .timeout(Duration.ofSeconds(10))
            .build();
        HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofFile(tmpLogFile));

        SourceService service = new SourceService();
        logsPath = service.getLogFilesBySources(List.of(
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
            tmpLogFile.toString()
        ));
    }

    @AfterAll
    static void removeFiles() throws IOException {
        Files.walk(PARENT_PATH)
            .filter(Files::isRegularFile)
            .forEach(it -> {
                try {
                    Files.delete(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @Test
    void getLogsStream() throws IOException {
        Stream<LogInfo> stream = parser.getLogsStream(logsPath);

        long logsCount = stream.count();

        assertThat(logsCount).isPositive();
    }

}
