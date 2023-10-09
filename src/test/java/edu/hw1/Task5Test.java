package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("5.Особый палиндром")
class Task5Test {

    @Test
    @DisplayName("Проверка на палиндром")
    void isPalindrome() {
        //given
        int num1 = 123321;
        int num2 = 1111;
        int num3 = 121;
        int num4 = 220;
        int num5 = 1;
        int num6 = 0;
        int num7 = -123321;

        //when
        boolean res1 = Task5.isPalindrome(num1);
        boolean res2 = Task5.isPalindrome(num2);
        boolean res3 = Task5.isPalindrome(num3);
        boolean res4 = Task5.isPalindrome(num4);
        boolean res5 = Task5.isPalindrome(num5);
        boolean res6 = Task5.isPalindrome(num6);
        boolean res7 = Task5.isPalindrome(num7);

        //then
        assertThat(res1).isEqualTo(true);
        assertThat(res2).isEqualTo(true);
        assertThat(res3).isEqualTo(true);
        assertThat(res4).isEqualTo(false);
        assertThat(res5).isEqualTo(true);
        assertThat(res6).isEqualTo(true);
        assertThat(res7).isEqualTo(true);
    }

    @Test
    @DisplayName("Получение потомка от числа")
    void getNumChild() {
        //given
        int num1 = 11211230;
        int num2 = 2333;
        int num3 = 56;
        int num4 = 5665;
        int num5 = 111;
        int num6 = 99999999;
        int num7 = 999999999;

        //when
        int res1 = Task5.getNumChild(num1);
        int res2 = Task5.getNumChild(num2);
        int res3 = Task5.getNumChild(num3);
        int res4 = Task5.getNumChild(num4);
        int res5 = Task5.getNumChild(num5);
        int res6 = Task5.getNumChild(num6);
        int res7 = Task5.getNumChild(num7);

        //then
        assertThat(res1).isEqualTo(2333);
        assertThat(res2).isEqualTo(56);
        assertThat(res3).isEqualTo(11);
        assertThat(res4).isEqualTo(1111);
        assertThat(res5).isEqualTo(12);     // (0111) -> (0+1),(1+1) -> 12
        assertThat(res6).isEqualTo(18181818);
        assertThat(res7).isEqualTo(918181818);  // (999999999) -> (0+9),(9+9),... -> 918181818

    }

    @Test
    @DisplayName("Основной метод")
    void isPalindromeDescendant() {
        //given
        int num1 = 11211230;
        int num2 = 13001120;
        int num3 = 23336014;
        int num4 = 11;
        int num5 = 99999999;
        int num6 = 99999;
        int num7 = 5556;

        //when
        boolean res1 = Task5.isPalindromeDescendant(num1);
        boolean res2 = Task5.isPalindromeDescendant(num2);
        boolean res3 = Task5.isPalindromeDescendant(num3);
        boolean res4 = Task5.isPalindromeDescendant(num4);
        boolean res5 = Task5.isPalindromeDescendant(num5);
        boolean res6 = Task5.isPalindromeDescendant(num6);
        boolean res7 = Task5.isPalindromeDescendant(num7);

        //then
        assertThat(res1).isEqualTo(true);
        assertThat(res2).isEqualTo(true);
        assertThat(res3).isEqualTo(true);
        assertThat(res4).isEqualTo(true);
        assertThat(res5).isEqualTo(true);
        assertThat(res6).isEqualTo(true);   // (099999)->(91818)->(0999)->(0918)->(99)
        assertThat(res7).isEqualTo(false);  // (5556)->(1011)->(12)->(6)

    }

}
