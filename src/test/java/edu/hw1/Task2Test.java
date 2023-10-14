package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("2.Количество цифр")
class Task2Test {

    @Test
    void countDigits() {

        //given
        int num1 = 2;
        int num2 = 1234567890;
        int num3 = 0;
        int num4 = -234123;

        //when
        int res1 = Task2.countDigits(num1);
        int res2 = Task2.countDigits(num2);
        int res3 = Task2.countDigits(num3);
        int res4 = Task2.countDigits(num4);

        //then
        assertThat(res1).isEqualTo(1);
        assertThat(res2).isEqualTo(10);
        assertThat(res3).isEqualTo(1);
        assertThat(res4).isEqualTo(6);

    }

}
