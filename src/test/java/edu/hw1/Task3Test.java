package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("3.Вложенный массив")
class Task3Test {

    @Test
    void isNestable() {

        //given
        int[] mas11 = new int[] {1, 2, 3, 4};
        int[] mas12 = new int[] {0, 6};

        int[] mas21 = new int[] {3, 1};
        int[] mas22 = new int[] {4, 0};

        int[] mas31 = new int[] {9, 9, 8};
        int[] mas32 = new int[] {8, 9};

        int[] mas41 = new int[] {1, 2, 3, 4};
        int[] mas42 = new int[] {2, 3};

        int[] mas51 = new int[] {};
        int[] mas52 = new int[] {2, 4, 5};

        int[] mas61 = new int[2];
        int[] mas62 = new int[] {2, 4, 5};

        //when
        boolean res1 = Task3.isNestable(mas11, mas12);
        boolean res2 = Task3.isNestable(mas21, mas22);
        boolean res3 = Task3.isNestable(mas31, mas32);
        boolean res4 = Task3.isNestable(mas41, mas42);
        boolean res5 = Task3.isNestable(mas51, mas52);
        boolean res6 = Task3.isNestable(mas61, mas62);

        //then
        assertThat(res1).isEqualTo(true);
        assertThat(res2).isEqualTo(true);
        assertThat(res3).isEqualTo(false);
        assertThat(res4).isEqualTo(false);
        assertThat(res5).isEqualTo(true);
        assertThat(res6).isEqualTo(false);

    }
}
