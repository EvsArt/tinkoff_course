package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task8Test {

    Task8 task8 = new Task8();

    @Test
    void isEvenLength() {

        String inp1 = "1101";
        String inp2 = "110";
        String inp3 = "11";
        String inp4 = "0";
        String inp5 = "";

        assertThat(task8.isEvenLength(inp1)).isTrue();
        assertThat(task8.isEvenLength(inp2)).isFalse();
        assertThat(task8.isEvenLength(inp3)).isTrue();
        assertThat(task8.isEvenLength(inp4)).isFalse();
        assertThat(task8.isEvenLength(inp5)).isTrue();

        assertThrows(NullPointerException.class, () -> task8.isEvenLength(null));
        assertThrows(IllegalArgumentException.class, () -> task8.isEvenLength("010s1"));

    }

    @Test
    void isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength() {

        String inp1 = "1101";
        String inp2 = "110";
        String inp3 = "0101";
        String inp4 = "001";
        String inp5 = "";

        assertThat(task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp1)).isTrue();
        assertThat(task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp2)).isFalse();
        assertThat(task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp3)).isFalse();
        assertThat(task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp4)).isTrue();
        assertThat(task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp5)).isFalse();

        assertThrows(
            NullPointerException.class,
            () -> task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(null)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength("010s1")
        );

    }

    @Test
    void countOfZerosIsEquals3() {

        String inp1 = "1101000";
        String inp2 = "11000";
        String inp3 = "11";
        String inp4 = "000";
        String inp5 = "";

        assertThat(task8.countOfZerosIsEquals3(inp1)).isFalse();
        assertThat(task8.countOfZerosIsEquals3(inp2)).isTrue();
        assertThat(task8.countOfZerosIsEquals3(inp3)).isFalse();
        assertThat(task8.countOfZerosIsEquals3(inp4)).isTrue();
        assertThat(task8.countOfZerosIsEquals3(inp5)).isFalse();

        assertThrows(NullPointerException.class, () -> task8.countOfZerosIsEquals3(null));
        assertThrows(IllegalArgumentException.class, () -> task8.countOfZerosIsEquals3("010s1"));

    }

    @Test
    void isNot11Or111() {

        String inp1 = "110";
        String inp2 = "1110";
        String inp3 = "11";
        String inp4 = "111";
        String inp5 = "";

        assertThat(task8.isNot11Or111(inp1)).isTrue();
        assertThat(task8.isNot11Or111(inp2)).isTrue();
        assertThat(task8.isNot11Or111(inp3)).isFalse();
        assertThat(task8.isNot11Or111(inp4)).isFalse();
        assertThat(task8.isNot11Or111(inp5)).isTrue();

        assertThrows(NullPointerException.class, () -> task8.isNot11Or111(null));
        assertThrows(IllegalArgumentException.class, () -> task8.isNot11Or111("010s1"));

    }

    @Test
    void isEveryNotEvenSymbolIs1() {

        String inp1 = "111";
        String inp2 = "1010";
        String inp3 = "111110101";
        String inp4 = "110";
        String inp5 = "1101";
        String inp6 = "011";
        String inp7 = "0111";

        assertThat(task8.isEveryNotEvenSymbolIs1(inp1)).isTrue();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp2)).isTrue();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp3)).isTrue();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp4)).isFalse();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp5)).isFalse();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp6)).isFalse();
        assertThat(task8.isEveryNotEvenSymbolIs1(inp7)).isFalse();

        assertThrows(NullPointerException.class, () -> task8.isEveryNotEvenSymbolIs1(null));
        assertThrows(IllegalArgumentException.class, () -> task8.isEveryNotEvenSymbolIs1("010s1"));

    }

    @Test
    void containsNotLessThan2ZerosAndNotMoreThan1Ones() {

        String inp1 = "100";
        String inp2 = "0001";
        String inp3 = "0001000";
        String inp4 = "00";
        String inp5 = "11";
        String inp6 = "10";

        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp1)).isTrue();
        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp2)).isTrue();
        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp3)).isTrue();
        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp4)).isTrue();
        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp5)).isFalse();
        assertThat(task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp6)).isFalse();

        assertThrows(NullPointerException.class, () -> task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(null));
        assertThrows(IllegalArgumentException.class, () -> task8.containsNotLessThan2ZerosAndNotMoreThan1Ones("010s1"));

    }

    @Test
    void notHaveSerialOnes() {

        String inp1 = "10101000";
        String inp2 = "11000";
        String inp3 = "11";
        String inp4 = "000";
        String inp5 = "00100110";
        String inp6 = "";

        assertThat(task8.notHaveSerialOnes(inp1)).isTrue();
        assertThat(task8.notHaveSerialOnes(inp2)).isFalse();
        assertThat(task8.notHaveSerialOnes(inp3)).isFalse();
        assertThat(task8.notHaveSerialOnes(inp4)).isTrue();
        assertThat(task8.notHaveSerialOnes(inp5)).isFalse();
        assertThat(task8.notHaveSerialOnes(inp6)).isTrue();

        assertThrows(NullPointerException.class, () -> task8.notHaveSerialOnes(null));
        assertThrows(IllegalArgumentException.class, () -> task8.notHaveSerialOnes("010s1"));

    }
}
