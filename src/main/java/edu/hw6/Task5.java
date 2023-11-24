package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

public class Task5 {

    @Slf4j
    static class HackerNews {

        private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
        private static final int SUCCESS_CODE = 200;

        public long[] hackerNewsTopStories() {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                    .timeout(TIMEOUT_DURATION)
                    .build();

                HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != SUCCESS_CODE) {
                    throw new IOException();
                }

                return Arrays.stream(
                        response.body().replaceAll("[\\[\\]]", "").split(",")
                    )
                    .mapToLong(Long::parseLong)
                    .toArray();

            } catch (URISyntaxException | InterruptedException e) {
                log.error("Error with connection to hacker-news in method hackerNewsTopStories()");
                throw new RuntimeException(e);
            } catch (IOException ignore) {
                return new long[0];
            }

        }

        public String news(long id) {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(String.format("https://hacker-news.firebaseio.com/v0/item/%d.json", id)))
                    .timeout(TIMEOUT_DURATION)
                    .build();

                HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != SUCCESS_CODE) {
                    throw new IOException();
                }

                Pattern pattern = Pattern.compile("\"title\":\"(.*)\",\"type\"");
                Matcher matcher = pattern.matcher(response.body());
                if (matcher.find()) {
                    return matcher.group(1);
                } else {
                    return "";
                }

            } catch (URISyntaxException | InterruptedException e) {
                log.error("Error with connection to hacker-news in method news()");
                throw new RuntimeException(e);
            } catch (IOException e) {
                return "";
            }

        }

    }

}
