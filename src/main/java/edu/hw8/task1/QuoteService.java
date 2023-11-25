package edu.hw8.task1;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QuoteService {

    public static final String QUOTE_NOT_FOUND_MSG = "Цитата не найдена(";
    private static final String QUOTE_SERVER =
        "https://kartaslov.ru/%D1%86%D0%B8%D1%82%D0%B0%D1%82%D1%8B-%D1%81%D0%BE-%D1%81%D0%BB%D0%BE%D0%B2%D0%BE%D0%BC/";
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);

    protected String getQuoteByWord(String keyWord) {
        Objects.requireNonNull(keyWord);

        HttpRequest request;
        try {
            URI uri = new URI(QUOTE_SERVER).resolve(keyWord);
            request = HttpRequest.newBuilder()
                .GET()
                .timeout(TIMEOUT_DURATION)
                .uri(uri)
                .build();
        } catch (URISyntaxException e) {
            log.error(String.format("Error with URL syntax: %s", QUOTE_SERVER));
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
        } catch (IOException | InterruptedException e) {
            log.error(String.format("Error with sending request to %s", QUOTE_SERVER));
            throw new RuntimeException(e);
        }

        return getQuoteFromHTMLString(response);
    }

    private String getQuoteFromHTMLString(String html) {

        Pattern pattern = Pattern.compile(
            "<meta\\sname=\"description\"\\scontent=\"(.*)\\sБольше\\sцитат\\sвы\\sнайдёте\\sна\\sсайте"
        );
        Matcher matcher = pattern.matcher(html);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return QUOTE_NOT_FOUND_MSG;
    }

}
