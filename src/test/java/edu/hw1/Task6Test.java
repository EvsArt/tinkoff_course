package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("6.Постоянная Капрекара")
class Task6Test {

    @Test
    @DisplayName("Основной метод")
    void countK() {
        //given
        int num1 = 6621;
        int num2 = 6554;
        int num3 = 1234;
        int badNum1 = 1000;
        int badNum2 = 10000;

        //when
        int res1 = Task6.countK(num1);
        int res2 = Task6.countK(num2);
        int res3 = Task6.countK(num3);
        int badRes1 = Task6.countK(badNum1);
        int badRes2 = Task6.countK(badNum2);

        //then
        assertThat(res1).isEqualTo(5);
        assertThat(res2).isEqualTo(4);
        assertThat(res3).isEqualTo(3);
        assertThat(badRes1).isEqualTo(-1);
        assertThat(badRes2).isEqualTo(-1);

    }

    @Test
    @DisplayName("Функция Капрекары")
    void kaprekaraFunc() {
        //given
        String num1 = "3524";
        String num2 = "3087";
        String num3 = "8352";
        String num4 = "6174";

        //when
        String res1 = Task6.kaprekaraFunc(num1);
        String res2 = Task6.kaprekaraFunc(num2);
        String res3 = Task6.kaprekaraFunc(num3);
        String res4 = Task6.kaprekaraFunc(num4);

        //then
        assertThat(res1).isEqualTo("3087");
        assertThat(res2).isEqualTo("8352");
        assertThat(res3).isEqualTo("6174");
        assertThat(res4).isEqualTo("6174");

    }
}
