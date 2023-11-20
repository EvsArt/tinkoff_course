package edu.hw5;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task5Test {

    Task5 task5 = new Task5();

    @ParameterizedTest
    @DisplayName("Check validation of car license plate number")
    @CsvSource(value = {
        "А123ВЕ777, true",
        "О777ОО177, true",
        "123АВЕ777, false",
        "А123ВГ77, false",
        "А123ВЕ7777, false",
        "' ', false"
    })
    void validCarLicensePlateNumberTest(String number, boolean isValid) {

        var res = task5.validCarLicensePlateNumber(number);

        assertThat(res).isEqualTo(isValid);

    }

    @Test
    @DisplayName("Check validation of null car license plate number")
    void validNullCarLicensePlateNumberTest() {

        String number = null;

        assertThrows(NullPointerException.class, () -> task5.validCarLicensePlateNumber(number));
    }

}
