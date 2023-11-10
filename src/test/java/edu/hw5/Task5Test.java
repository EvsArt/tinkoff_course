package edu.hw5;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task5Test {

    Task5 task5 = new Task5();

    @Test
    @DisplayName("Check validation of car license plate number")
    void validCarLicensePlateNumberTest() {

        Map<String, Boolean> numbers = Map.of(
            "А123ВЕ777", true,
            "О777ОО177", true,
            "123АВЕ777", false,
            "А123ВГ77", false,
            "А123ВЕ7777", false,
            "", false
        );

        for (var entry : numbers.entrySet()) {
            assertThat(
                task5.validCarLicensePlateNumber(entry.getKey()))
                .isEqualTo(entry.getValue());
        }

        assertThrows(NullPointerException.class, () -> task5.validCarLicensePlateNumber(null));

    }
}
