package edu.hw5;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task4Test {

    Task4 task4 = new Task4();

    @ParameterizedTest
    @DisplayName("Test password checking")
    @CsvSource(value = {
        "Awsdawsd, false",
        "Passw!awse, true",
        "' ', false",
        "!, true",
        "|, true"
    })
    void checkPasswordTest(String password, boolean isValid) {

        var res = task4.checkPassword(password);

        assertThat(res).isEqualTo(isValid);

        assertThrows(NullPointerException.class, () -> task4.checkPassword(null));

    }

    @Test
    @DisplayName("Test null password checking")
    void checkNullPasswordTest() {

        String password = null;

        assertThrows(NullPointerException.class, () -> task4.checkPassword(password));

    }

}
