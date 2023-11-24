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

        var res1 = task8.isEvenLength(inp1);
        var res2 = task8.isEvenLength(inp2);
        var res3 = task8.isEvenLength(inp3);
        var res4 = task8.isEvenLength(inp4);
        var res5 = task8.isEvenLength(inp5);

        assertThat(res1).isTrue();
        assertThat(res2).isFalse();
        assertThat(res3).isTrue();
        assertThat(res4).isFalse();
        assertThat(res5).isTrue();

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

        var res1 = task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp1);
        var res2 = task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp2);
        var res3 = task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp3);
        var res4 = task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp4);
        var res5 = task8.isStartsWith0AndNotEvenLengthOrStartsWith1AndEvenLength(inp5);

        assertThat(res1).isTrue();
        assertThat(res2).isFalse();
        assertThat(res3).isFalse();
        assertThat(res4).isTrue();
        assertThat(res5).isFalse();

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

        var res1 = task8.countOfZerosIsEquals3(inp1);
        var res2 = task8.countOfZerosIsEquals3(inp2);
        var res3 = task8.countOfZerosIsEquals3(inp3);
        var res4 = task8.countOfZerosIsEquals3(inp4);
        var res5 = task8.countOfZerosIsEquals3(inp5);

        assertThat(res1).isFalse();
        assertThat(res2).isTrue();
        assertThat(res3).isFalse();
        assertThat(res4).isTrue();
        assertThat(res5).isFalse();

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

        var res1 = task8.isNot11Or111(inp1);
        var res2 = task8.isNot11Or111(inp2);
        var res3 = task8.isNot11Or111(inp3);
        var res4 = task8.isNot11Or111(inp4);
        var res5 = task8.isNot11Or111(inp5);

        assertThat(res1).isTrue();
        assertThat(res2).isTrue();
        assertThat(res3).isFalse();
        assertThat(res4).isFalse();
        assertThat(res5).isTrue();

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

        var res1 = task8.isEveryNotEvenSymbolIs1(inp1);
        var res2 = task8.isEveryNotEvenSymbolIs1(inp2);
        var res3 = task8.isEveryNotEvenSymbolIs1(inp3);
        var res4 = task8.isEveryNotEvenSymbolIs1(inp4);
        var res5 = task8.isEveryNotEvenSymbolIs1(inp5);
        var res6 = task8.isEveryNotEvenSymbolIs1(inp6);
        var res7 = task8.isEveryNotEvenSymbolIs1(inp7);

        assertThat(res1).isTrue();
        assertThat(res2).isTrue();
        assertThat(res3).isTrue();
        assertThat(res4).isFalse();
        assertThat(res5).isFalse();
        assertThat(res6).isFalse();
        assertThat(res7).isFalse();

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

        var res1 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp1);
        var res2 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp2);
        var res3 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp3);
        var res4 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp4);
        var res5 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp5);
        var res6 = task8.containsNotLessThan2ZerosAndNotMoreThan1Ones(inp6);

        assertThat(res1).isTrue();
        assertThat(res2).isTrue();
        assertThat(res3).isTrue();
        assertThat(res4).isTrue();
        assertThat(res5).isFalse();
        assertThat(res6).isFalse();

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

        var res1 = task8.notHaveSerialOnes(inp1);
        var res2 = task8.notHaveSerialOnes(inp2);
        var res3 = task8.notHaveSerialOnes(inp3);
        var res4 = task8.notHaveSerialOnes(inp4);
        var res5 = task8.notHaveSerialOnes(inp5);
        var res6 = task8.notHaveSerialOnes(inp6);

        assertThat(res1).isTrue();
        assertThat(res2).isFalse();
        assertThat(res3).isFalse();
        assertThat(res4).isTrue();
        assertThat(res5).isFalse();
        assertThat(res6).isTrue();

        assertThrows(NullPointerException.class, () -> task8.notHaveSerialOnes(null));
        assertThrows(IllegalArgumentException.class, () -> task8.notHaveSerialOnes("010s1"));

    }
}
