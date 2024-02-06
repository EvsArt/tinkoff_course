package edu.hw7;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task1Test {

    Task1 task1 = new Task1();

    @Test
    void addValueToCounterAtEachOfNThreads() {

        int value = 1_000_000_000;
        int threadsNum = 1280;

        long res = task1.addValueToCounterAtEachOfNThreads(value, threadsNum);

        long expRes = value;

        assertThat(res).isEqualTo(expRes);

    }

}
