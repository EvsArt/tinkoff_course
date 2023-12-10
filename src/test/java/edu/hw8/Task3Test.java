package edu.hw8;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task3Test {

    String infoFromDB = """
        a.v.petrov  81dc9bdb52d04dc20036dbd8313ed055
        v.v.belov   4229d691b07b13341da53f17ab9f2416
        a.s.ivanov  153e98d43b1d4fc021f5af8f6e4d00f1
        k.p.maslov  f2ce11ebf110993621bedd8e747d7b1b
        a.s.evseev  4124bc0a9335c27f086f24ba207a4912""";

    @ParameterizedTest
    @CsvSource(value = {
        "hello, hellp",
        "hellz, helm0",
        "hell9, hella",
        "zzzz, 00000",
        "'', 0"
    })
    void nextPasswordTest(String pass, String expNextPass) {
        // given
        Task3 task3 = new Task3(infoFromDB);

        // when
        String nextPath = task3.nextPassword(pass);

        // then
        assertEquals(expNextPass, nextPath);

    }

    @Test
    void multiThreadingIsFasterTest() {
        // given
        Task3 task30 = new Task3(infoFromDB);
        Task3 task31 = new Task3(infoFromDB);
        int maxPassLength = 4;
        int threadsCount = 8;

        // when
        long time1 = System.nanoTime();
        System.out.println(task30.hackBD(maxPassLength));
        time1 = System.nanoTime() - time1;

        long time2 = System.nanoTime();
        System.out.println(task31.hackBDInMultiThread(maxPassLength, threadsCount));
        time2 = System.nanoTime() - time2;

        // then
        assertThat(time1).isGreaterThan(time2);

    }

    @Test
    void getTimeProfit() {
        // No test data, only output
        for (int i = 1; i <= 64; i *= 2) {
            long threadTimeSum = System.nanoTime();
            for (int k = 0; k < 10; k++) {
                Task3 work = new Task3(infoFromDB);
                work.hackBDInMultiThread(4, i);
            }
            threadTimeSum = System.nanoTime() - threadTimeSum;
            long averageTimeInMs = TimeUnit.NANOSECONDS.toMillis(threadTimeSum / 10);
            System.out.printf(
                "Average time for %d-threading code is %dms%n",
                i,
                averageTimeInMs
            );
        }

    }

}
