package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("1.Время в секунды")
public class Task1Test {
    @Test
    void minutesToSeconds() {

        //given
        String goodTime1 = "13:26";
        String goodTime2 = "00:00";
        String goodTime3 = "61:59";
        String badTime1 = "1:60";
        String badTime2 = "1:61";
        String badTime3 = "-4:13";

        //when
        int goodSeconds1 = Task1.minutesToSeconds(goodTime1);
        int goodSeconds2 = Task1.minutesToSeconds(goodTime2);
        int goodSeconds3 = Task1.minutesToSeconds(goodTime3);
        int badSeconds1 = Task1.minutesToSeconds(badTime1);
        int badSeconds2 = Task1.minutesToSeconds(badTime2);
        int badSeconds3 = Task1.minutesToSeconds(badTime3);

        //then
        assertThat(goodSeconds1).isEqualTo(806);
        assertThat(goodSeconds2).isEqualTo(0);
        assertThat(goodSeconds3).isEqualTo(3719);
        assertThat(badSeconds1).isEqualTo(-1);
        assertThat(badSeconds2).isEqualTo(-1);
        assertThat(badSeconds3).isEqualTo(-1);
    }
}
