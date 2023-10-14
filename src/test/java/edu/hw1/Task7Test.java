package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("7.Циклический битовый сдвиг")
class Task7Test {

    @Test
    @DisplayName("Сдвиг влево")
    void rotateLeft() {
        //given
        int n2 = 16;
        int shift2 = 1;

        int n3 = 17;
        int shift3 = 2;

        int n4 = 0;
        int shift4 = 1;

        //when
        int res2 = Task7.rotateLeft(n2, shift2);
        int res3 = Task7.rotateLeft(n3, shift3);
        int res4 = Task7.rotateLeft(n4, shift4);

        //then
        assertThat(res2).isEqualTo(1);
        assertThat(res3).isEqualTo(6);
        assertThat(res4).isEqualTo(-1);

    }

    @Test
    @DisplayName("Сдвиг вправо")
    void rotateRight() {
        //given
        int n1 = 8;
        int shift1 = 1;

        int n2 = 15;
        int shift2 = 3;

        int n3 = 11;
        int shift3 = 3;

        //when
        int res1 = Task7.rotateRight(n1, shift1);
        int res2 = Task7.rotateRight(n2, shift2);
        int res3 = Task7.rotateRight(n3, shift3);

        //then
        assertThat(res1).isEqualTo(4);
        assertThat(res2).isEqualTo(15);
        assertThat(res3).isEqualTo(7);

    }
}
