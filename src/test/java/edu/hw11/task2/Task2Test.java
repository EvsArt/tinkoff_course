package edu.hw11.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @Test
    void getSum() {
        // given
        Task2 task2 = new Task2();

        // when
        int before = task2.getSum(3,3);
        task2.changeSumToMul();
        int after = task2.getSum(3,3);

        //then
        assertEquals(6, before);
        assertEquals(9, after);

    }
}
