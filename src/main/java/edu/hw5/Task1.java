package edu.hw5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

public class Task1 {

    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_MINUTE = 60;

    public String getAverageTime(String timeSections) {

        List<Duration> durations = getDurationsByTimeSections(timeSections);
        Duration averageDuration = getAverageDuration(durations);
        long hours = averageDuration.getSeconds() / SECONDS_IN_HOUR;
        long minutes = averageDuration.getSeconds() / SECONDS_IN_MINUTE % SECONDS_IN_MINUTE;

        return String.format("%dч. %dм.", hours, minutes);
    }

    public List<Duration> getDurationsByTimeSections(String timeSections) {
        if (timeSections == null) {
            return List.of();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm");

        return timeSections
            .lines()
            .map(line -> {
                String[] startAndEnd = line.split(" - ");
                try {
                    if (startAndEnd.length != 2) {
                        throw new ParseException("Timestamp must contains only 1 \" - \"!", 0);
                    }
                    Temporal start = formatter.parse(startAndEnd[0]).toInstant();
                    Temporal end = formatter.parse(startAndEnd[1]).toInstant();

                    return Duration.between(start, end);
                } catch (ParseException e) {
                    return Duration.ofHours(-1);    // Wrong time sections will be ignored
                }
            })
            .filter(it -> !it.isNegative())     // Filter wrong time sections
            .toList();
    }

    public Duration getAverageDuration(List<Duration> durations) {
        if (durations == null) {
            return Duration.ZERO;
        }

        long seconds = durations
            .stream()
            .collect(
                Collectors.averagingLong(Duration::getSeconds)
            )
            .longValue();

        return Duration.ofSeconds(seconds);

    }
}
