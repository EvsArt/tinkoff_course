package edu.hw5;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task1Test {

    Task1 task1 = new Task1();

    @Test
    void getAverageTime() {

        String input1 =
            "2022-03-12, 20:20 - 2022-03-12, 23:50\n" +
                "2022-04-01, 21:30 - 2022-04-02, 01:20";
        String input2 =
            "2022-03-12, 20:00 - 2022-03-12, 20:00\n" +
                "2022-03-12, 20:00 - 2022-03-13, 20:00";

        String res1 = task1.getAverageTime(input1);
        String res2 = task1.getAverageTime(input2);

        String expRes1 = "3ч. 40м.";
        String expRes2 = "12ч. 0м.";

        assertThat(res1).isEqualTo(expRes1);
        assertThat(res2).isEqualTo(expRes2);

    }

    @Test
    void getAverageTimeWithNulls() {

        String emptyInput = "";
        String nullInput = null;

        String res1 = task1.getAverageTime(emptyInput);
        String res2 = task1.getAverageTime(nullInput);

        String expRes = "0ч. 0м.";

        assertThat(res1).isEqualTo(expRes);
        assertThat(res2).isEqualTo(expRes);

    }

    @Test
    void getDurationsByTimeSections() {

        String input =
            """
                2022-03-12, 20:20 - 2022-03-12, 23:50
                2022-04-01, 21:30 - 2022-04-02, 01:20
                2022-04-01, 21:59 - 2022-04-01, 22:00
                """;

        List<Duration> res = task1.getDurationsByTimeSections(input);

        List<Duration> expRes = List.of(
            Duration.ofHours(3).plusMinutes(30),
            Duration.ofHours(3).plusMinutes(50),
            Duration.ofMinutes(1)
        );

        assertThat(res).isEqualTo(expRes);
    }

    @Test
    void getDurationsByTimeSectionsWithNulls() {

        String wrongTimeSectionInput = "2022-03-12, 20:20 - 2022-03-12, 20:19";
        String wrongTimeFormatInput1 = "22-03-12, 20.00 : 22-03-12, 20.19";
        String wrongTimeFormatInput2 = "22/03/12, 20.00 : 22/03/12, 20.19";
        String emptyInput = "";
        String nullInput = null;

        List<Duration> res1 = task1.getDurationsByTimeSections(wrongTimeSectionInput);
        List<Duration> res2 = task1.getDurationsByTimeSections(wrongTimeFormatInput1);
        List<Duration> res3 = task1.getDurationsByTimeSections(wrongTimeFormatInput2);
        List<Duration> res4 = task1.getDurationsByTimeSections(emptyInput);
        List<Duration> res5 = task1.getDurationsByTimeSections(nullInput);

        List<Duration> emptyListRes = List.of();

        Stream.of(res1, res2, res3, res4, res5)
            .forEach(it -> assertThat(it).isEqualTo(emptyListRes));

    }

    @Test
    void getAverageDuration() {

        List<Duration> durations = List.of(
            Duration.ofHours(2).plusMinutes(30),
            Duration.ofHours(3).plusMinutes(30),
            Duration.ZERO,
            Duration.ofHours(24)
        );

        Duration res = task1.getAverageDuration(durations);

        Duration expRes = Duration.ofHours(7).plusMinutes(30);

        assertThat(res).isEqualTo(expRes);

    }

    @Test
    void getAverageDurationWithNulls() {

        List<Duration> durations = List.of();
        List<Duration> durationsNull = null;

        Duration res = task1.getAverageDuration(durations);
        Duration resNull = task1.getAverageDuration(durationsNull);

        Duration expRes = Duration.ZERO;

        assertThat(res).isEqualTo(expRes);
        assertThat(resNull).isEqualTo(expRes);

    }

}
