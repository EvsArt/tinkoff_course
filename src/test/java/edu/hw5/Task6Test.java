package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task6Test {

    Task6 task6 = new Task6();

    @Test
    @DisplayName("Checking existence substring in string")
    void checkSIsSubstringT() {

        String str1 = "achfdbaabgabcaabg";
        String inStr1 = "abc";
        String str2 = "Asdw";
        String inStr2 = "swd";
        String str3 = "";
        String inStr3 = "";
        String str4 = "awv";
        String inStr4 = "awv";
        String str5 = "Aaswd";
        String inStr5 = "aaswd";

        assertThat(task6.checkSIsSubstringT(inStr1, str1)).isTrue();
        assertThat(task6.checkSIsSubstringT(inStr2, str2)).isFalse();
        assertThat(task6.checkSIsSubstringT(inStr3, str3)).isTrue();
        assertThat(task6.checkSIsSubstringT(inStr4, str4)).isTrue();
        assertThat(task6.checkSIsSubstringT(inStr5, str5)).isFalse();

        assertThrows(NullPointerException.class, () -> task6.checkSIsSubstringT(null, "A"));
        assertThrows(NullPointerException.class, () -> task6.checkSIsSubstringT("A",null));

    }
}
