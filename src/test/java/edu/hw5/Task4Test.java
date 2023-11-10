package edu.hw5;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task4Test {

    Task4 task4 = new Task4();

    @Test
    @DisplayName("Test password checking")
    void checkPasswordTest() {

        Map<String, Boolean> passwords = Map.of(
            "Awsdawsd", false,
            "Passw!awse", true,
            "", false,
            "!", true,
            "|", true
        );

        for (var entry : passwords.entrySet()) {
            assertThat(
                task4.checkPassword(entry.getKey()))
                .isEqualTo(entry.getValue());
        }

        assertThrows(NullPointerException.class, () -> task4.checkPassword(null));

    }
}
