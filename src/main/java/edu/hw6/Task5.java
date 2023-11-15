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

public class Task5 {

    static class HackerNews {

        private final Duration timeoutDuration = Duration.ofSeconds(10);
        private final int successCode = 200;

        public long[] hackerNewsTopStories() {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                    .timeout(timeoutDuration)
                    .build();

                HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != successCode) {
                    throw new IOException();
                }

                return Arrays.stream(
                        response.body().replaceAll("[\\[\\]]", "").split(",")
                    )
                    .mapToLong(Long::parseLong)
                    .toArray();

            } catch (URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException ignore) {
                return new long[0];
            }

        }

        public String news(long id) {

            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
                    .timeout(timeoutDuration)
                    .build();

                HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != successCode) {
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
                throw new RuntimeException(e);
            } catch (IOException e) {
                return "";
            }

        }

    }

}
