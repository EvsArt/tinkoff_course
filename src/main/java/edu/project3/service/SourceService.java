package edu.project3.service;

import edu.project3.Constants;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SourceService {

    private final Logger logger = LogManager.getLogger();

    private final Duration timeOutDuration = Duration.ofSeconds(10);

    public SourceService() {
        try {
            Files.createDirectories(Constants.localStoragePath);
        } catch (IOException e) {
            logger.error("Error with creating local storage directory!");
            throw new RuntimeException(e);
        }
    }

    public List<Path> getLogFilesBySources(List<String> sources) {
        List<Path> res = new ArrayList<>();
        sources.forEach(it -> {
            try {
                res.addAll(getLogFilesBySource(it));
            } catch (URISyntaxException | IOException e) {
                logger.error("Error with getting logs from file " + it);
                throw new RuntimeException(e);
            }
        });

        return res;

    }

    private List<Path> getLogFilesBySource(String source) throws URISyntaxException, IOException {
        List<Path> res = new ArrayList<>();
        if (Pattern.matches("^(\\w)*://(.*)$", source)) {
            res.add(copyUrlFileToLocalStorage(source));
        } else {
            res.addAll(findFilesByGlob(source));
        }
        return res;
    }

    private Path copyUrlFileToLocalStorage(String url) throws URISyntaxException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(url))
            .GET()
            .timeout(timeOutDuration)
            .build();

        String fileName = String.valueOf(url.hashCode());
        Files.createDirectories(Constants.webUrlCopyPath);
        Path localCopy = Constants.webUrlCopyPath
            .resolve(fileName);

        try {
            HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofFile(localCopy));
        } catch (IOException | InterruptedException e) {
            logger.error("Error with sending request to the server");
            throw new RuntimeException(e);
        }

        return localCopy;

    }

    protected List<Path> findFilesByGlob(String glob) throws IOException {
        PathMatcher matcher = Constants.localStoragePath.getFileSystem().getPathMatcher("glob:**/" + glob);
        try (Stream<Path> filesStream = Files.walk(Constants.localStoragePath)) {
            return filesStream
                .filter(matcher::matches)
                .toList();
        }
    }

}
