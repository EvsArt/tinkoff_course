package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task6Test {

    Task6 task6 = new Task6();

    @ParameterizedTest
    @DisplayName("Checking existence substring in string")
    @CsvSource(value = {
        "abc, achfdbaabgabcaabg, true",
        "Asdw, swd, false",
        "' ', ' ', true",
        "awv, awv, true",
        "Aaswd, aaswd, false",
    })
    void checkSIsSubstringT(String str1, String str2, boolean isSubstring) {

        assertThat(task6.checkSIsSubstringT(str1, str2)).isEqualTo(isSubstring);

    }

    @Test
    @DisplayName("Checking null substrings")
    void checkNullSubstrings(){
        assertThrows(NullPointerException.class, () -> task6.checkSIsSubstringT(null, "A"));
        assertThrows(NullPointerException.class, () -> task6.checkSIsSubstringT("A",null));
    }

}
