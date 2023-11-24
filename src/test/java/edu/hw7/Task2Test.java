package edu.hw7;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task2Test {

    Task2 task2 = new Task2();

    @Test
    void getFactorial() {

        int value = 23;

        BigInteger res = task2.getFactorial(value);

        BigInteger expRes = new BigInteger("25852016738884976640000");
        assertThat(res).isEqualTo(expRes);

    }
}
