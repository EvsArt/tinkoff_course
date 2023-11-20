package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task7Test {

    Task7 task7 = new Task7();

    @Test
    @DisplayName("Test 1 condition")
    void hasNotLessThan3SymbolsAnd3dSymbolIs0Test() {

        String inp1 = "11011";
        String inp2 = "110";
        String inp3 = "11111";
        String inp4 = "00";

        var res1 = task7.hasNotLessThan3SymbolsAnd3dSymbolIs0(inp1);
        var res2 = task7.hasNotLessThan3SymbolsAnd3dSymbolIs0(inp2);
        var res3 = task7.hasNotLessThan3SymbolsAnd3dSymbolIs0(inp3);
        var res4 = task7.hasNotLessThan3SymbolsAnd3dSymbolIs0(inp4);

        assertThat(res1).isTrue();
        assertThat(res2).isTrue();
        assertThat(res3).isFalse();
        assertThat(res4).isFalse();

        assertThrows(NullPointerException.class, () -> task7.hasNotLessThan3SymbolsAnd3dSymbolIs0(null));
        assertThrows(IllegalArgumentException.class, () -> task7.hasNotLessThan3SymbolsAnd3dSymbolIs0("010s1"));

    }

    @Test
    @DisplayName("Test 2 condition")
    void isStartAndEndWithSimilarSymbolsTest() {

        String inp1 = "11011";
        String inp2 = "110";
        String inp3 = "11";
        String inp4 = "0";
        String inp5 = "1";

        var res1 = task7.isStartAndEndWithSimilarSymbols(inp1);
        var res2 = task7.isStartAndEndWithSimilarSymbols(inp2);
        var res3 = task7.isStartAndEndWithSimilarSymbols(inp3);
        var res4 = task7.isStartAndEndWithSimilarSymbols(inp4);
        var res5 = task7.isStartAndEndWithSimilarSymbols(inp5);

        assertThat(res1).isTrue();
        assertThat(res2).isFalse();
        assertThat(res3).isTrue();
        assertThat(res4).isTrue();
        assertThat(res5).isTrue();

        assertThrows(NullPointerException.class, () -> task7.isStartAndEndWithSimilarSymbols(null));
        assertThrows(IllegalArgumentException.class, () -> task7.isStartAndEndWithSimilarSymbols("010s1"));

    }

    @Test
    @DisplayName("Check 3 condition")
    void hasLengthNotLess1AndNotMore3Test() {

        String inp1 = "1101";
        String inp2 = "110";
        String inp3 = "11";
        String inp4 = "0";
        String inp5 = "";

        var res1 = task7.hasLengthNotLess1AndNotMore3(inp1);
        var res2 = task7.hasLengthNotLess1AndNotMore3(inp2);
        var res3 = task7.hasLengthNotLess1AndNotMore3(inp3);
        var res4 = task7.hasLengthNotLess1AndNotMore3(inp4);
        var res5 = task7.hasLengthNotLess1AndNotMore3(inp5);


        assertThat(res1).isFalse();
        assertThat(res2).isTrue();
        assertThat(res3).isTrue();
        assertThat(res4).isTrue();
        assertThat(res5).isFalse();

        assertThrows(NullPointerException.class, () -> task7.hasLengthNotLess1AndNotMore3(null));
        assertThrows(IllegalArgumentException.class, () -> task7.hasLengthNotLess1AndNotMore3("010s1"));

    }
}
